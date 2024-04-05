package com.appaznay.adhan

enum class Salawate(val salat: SalateTime) {
    SALATSOBH(SalateTime("الصبح", "04", "53", "15", false)),
    SALATTOHR(SalateTime("الظهر", "12", "44", "15", false)),
    SALATASR(SalateTime("العصر", "16", "19", "15", false)),
    SALATMAGHREB(SalateTime("المغرب", "19", "09", "05", false)),
    SALATICHA(SalateTime("العشاء", "20", "29", "10", false)),
    SALATCHOROK(SalateTime("الشروق", "06", "22", "0", false)),
    SALATJOMO3A(SalateTime("الجمعة", "12", "44", "15", false)),
}