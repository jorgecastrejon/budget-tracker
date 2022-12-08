package org.jcastrejon.features.register.ui

val fieldValidators: List<(String, String) -> FormFieldError?> = listOf(
    { field1: String, field2: String -> FormFieldError.Length.takeIf { field1.length < 6 || field2.length < 6 } },
    { field1: String, field2: String -> FormFieldError.FieldsDoNotMatch.takeIf { field1 != field2 } },
)

enum class FormFieldError {
    Length,
    FieldsDoNotMatch;
}