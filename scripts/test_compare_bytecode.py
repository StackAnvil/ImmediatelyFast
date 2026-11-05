"""Regression checks for the refactoring comparison's meaningful differences."""

import contextlib
import io
import unittest
import zipfile

from compare_bytecode import compare, normalize, resource_content


class BytecodeComparisonTest(unittest.TestCase):
    def compare_bodies(self, before, after, renames=None, allowed=None):
        with contextlib.redirect_stdout(io.StringIO()):
            compare({"common/test": before}, {"common/test": after}, renames or {}, allowed or set())

    def test_constant_pool_reordering_preserves_literal_text(self):
        before = "0: ldc #12 // String #123 oldField"
        after = "0: ldc #54 // String #123 oldField"
        self.compare_bodies({"Example.class": before}, {"Example.class": after}, {"oldField": "newField"})

    def test_literal_changes_fail_even_when_they_match_a_rename(self):
        for before, after in [("#123", "#456"), ("oldField", "newField")]:
            with self.subTest(before=before, after=after), self.assertRaises(SystemExit):
                self.compare_bodies(
                    {"Example.class": "0: ldc #12 // String " + before},
                    {"Example.class": "0: ldc #12 // String " + after},
                    {"oldField": "newField"},
                )

    def test_symbol_renames_match_but_instructions_must_stay_the_same(self):
        before = {"Old.class": "class Old {\n0: getfield #12 // Field oldField:I\n}"}
        after = {"New.class": "class New {\n0: getfield #54 // Field newField:I\n}"}
        self.compare_bodies(before, after, {"Old": "New", "oldField": "newField"})
        with self.assertRaises(SystemExit):
            self.compare_bodies(before, {"Old.class": "0: return"})

    def test_added_and_removed_classes_fail(self):
        for before, after in [({}, {"Example.class": "class Example {}"}), ({"Example.class": "class Example {}"}, {})]:
            with self.subTest(before=before), self.assertRaises(SystemExit):
                self.compare_bodies(before, after)

    def test_only_exact_allowed_class_is_exempt(self):
        self.compare_bodies({"pkg/Example.class": "before"}, {"pkg/Example.class": "after"}, allowed={"pkg.Example"})
        with self.assertRaises(SystemExit):
            self.compare_bodies({"pkg/Example$Nested.class": "before"}, {"pkg/Example$Nested.class": "after"}, allowed={"pkg.Example"})

    def test_structural_exemptions_do_not_allow_missing_classes(self):
        with self.assertRaises(SystemExit):
            self.compare_bodies({"Example.class": "before"}, {}, allowed={"Example"})

    def test_rename_collisions_fail(self):
        with self.assertRaises(ValueError):
            self.compare_bodies(
                {"Old.class": "class Old {}", "New.class": "class New {}"},
                {"New.class": "class New {}"}, {"Old": "New"},
            )

    def test_resource_numbers_are_preserved(self):
        self.assertNotEqual(normalize("color=#123", {}), normalize("color=#456", {}))

    def test_nested_jar_timestamps_are_ignored_but_content_is_preserved(self):
        def archive(year, content):
            output = io.BytesIO()
            with zipfile.ZipFile(output, "w") as jar:
                entry = zipfile.ZipInfo("metadata.json", (year, 1, 1, 0, 0, 0))
                jar.writestr(entry, content)
            return resource_content("dependency.jar", output.getvalue())

        self.assertEqual(archive(2020, "original"), archive(2026, "original"))
        self.assertNotEqual(archive(2020, "original"), archive(2026, "changed"))


if __name__ == "__main__":
    unittest.main()
