package com.appaznay.adhan

enum class Salawate(val salat: SalateTime) {
    SALATSOBH(SalateTime("elSobh", "06:00", "00:10")),
    SALATTOHR(SalateTime("elTohr", "12:55", "00:15")),
    SALATASR(SalateTime("elAsr", "21:30", "00:15")),
    SALATMAGHREB(SalateTime("elMaghreb", "06:50", "00:2")),
    SALATICHA(SalateTime("elIcha", "20:00", "00:15")),



}
