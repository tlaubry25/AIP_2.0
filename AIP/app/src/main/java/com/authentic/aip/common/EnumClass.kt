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
    VALIDE_COMPLET(20),
    EN_COURS(30),
    VALIDEE(40),
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
}