package app.foodin.api.common

import org.springframework.validation.BindingResult

class BindingException : RuntimeException() {
    //TODO
}


fun checkBindingException(bindingResult: BindingResult) {
    if (!bindingResult.hasErrors()) return
}