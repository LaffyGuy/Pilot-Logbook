package com.example.pilotlogbook.data.validation

fun validateDate(date: String): ValidationResult {
    if(date.isBlank()){
        return ValidationResult.Failed("The date can`t be empty")
    }
    return ValidationResult.Success
}

fun validateDeparturePlace(departurePlace: String): ValidationResult {
    if(departurePlace.isBlank()){
        return ValidationResult.Failed("The departure place can`t be empty")
    }
    return ValidationResult.Success
}

fun validateDepartureTime(departureTime: String): ValidationResult {
    if(departureTime.isBlank()){
        return ValidationResult.Failed("The departure time can`t be empty")
    }
    return ValidationResult.Success
}

fun validateArrivalPlace(arrivalPlace: String): ValidationResult {
    if(arrivalPlace.isBlank()){
        return ValidationResult.Failed("The arrival place can`t be empty")
    }
    return ValidationResult.Success
}

fun validateArrivalTime(arrivalTime: String): ValidationResult {
    if(arrivalTime.isBlank()){
        return ValidationResult.Failed("The arrival time can`t be empty")
    }
    return ValidationResult.Success
}

fun validateModel(model: String): ValidationResult {
    if(model.isBlank()){
        return ValidationResult.Failed("The model can`t be empty")
    }
    return ValidationResult.Success
}

fun validateRegistration(registration: String): ValidationResult {
    if(registration.isBlank()){
        return ValidationResult.Failed("The registration can`t be empty")
    }
    return ValidationResult.Success
}


