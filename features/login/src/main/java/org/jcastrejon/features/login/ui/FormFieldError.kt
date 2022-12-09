package org.jcastrejon.features.login.ui

val fieldValidators: List<(String) -> FormFieldError?> =
    listOf { field1: String -> FormFieldError.Length.takeIf { field1.length < 6 } }

enum class FormFieldError {
    Length,
    FieldsDoNotMatch;
}