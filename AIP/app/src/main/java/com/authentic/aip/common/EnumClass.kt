package com.authentic.aip.common

class EnumClass{

enum class TypeRequestEnum {
    ONGOING, DONE
}

enum class PreferencesEnum{
    SESSION_ID,
    REQUEST_ID
}

enum class StatusRequestEnum(val statusCode : Int){
    EN_CREATION(0),
    EN_COURS_VALIDATION(10),
    VALIDE_COMPLET(20),
    EN_COURS(30),
    APPROUVE_EN_ATTENTE(35),
    VALIDEE(40),
    DMOA_EN_CREATION(41),
    VALIDATION_EN_COURS(42),
    COMPLETE_APPROB_REQUIS(43),
    EN_COURS_APPROB(44),
    FERMEE(50),
    REFUSEE(90),
    ANNULEE(99)
}
enum class TypeAttachmentEnum(){
    WORD,
    PDF,
    ZIP,
    EXCEL,
    TXT
}
enum class ActionValidationEnum{
    VALID,
    REFUSE,
    EXPLAIN
}

}