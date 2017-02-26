Proovitöö "Raamatukogu"
===

Ülesanne
---
Palun loo väike rakendus, kus on raamatukogu funktsionaalsus (kataloog, laenutus ja tagastamine).
Rakendus koosneb kahest osast – UI ja server. Server peab pakkuma andmeid JSON formaadis üle REST endpointide. 
UI võib asuda ka serveri komponendi sees, aga peab kasutama andmete kuvamiseks ainult vastavaid REST teenuseid. 
REST teenused peavad olema kaetud integratsioonitestidega ning omama healthchecki. 
Rakenduse esitlemisel tuleb põhjendada valitud tehnoloogiaid ja loodud lahendust, sh vajadusel ka koodi selgitama.

Andmebaasi schema prototüüp
---
    Raamat  (id, pealkiri, aasta)
    Autor	(id, nimi, synniaasta, surmaaasta)
    Autorlus (id, raamat_id, lugeja_id)
    Lugeja 	(id, nimi)
    Laenutus (id, raamat_id, lugeja_id, algus, lopp)


API
---
    /raamatukogu
	    /kataloog
		    GET (filterTeos, filterAasta, filterAutor -> kataloog)
	    /laenuta
   		    POST (lugeja, teosID)
	    /tagastus
		    POST (lugeja, teosID)
	    /laenutused
		    GET (lugeja -> laenutused)

Valitud tehnoloogiad
___
Kuna antud projekt on suhteliselt minimalistlik prototüüp, sai valitud teegid eelkõige selle järgi, et koodi tuleks
kirjutada võimalikult vähe, samas, et see oleks võrdlemisi loetav ka ilma teekide käitumisse süvenemata.

* REST - Spark
    Minimalistlik, ilma suurema overheadita veebirakenduste raamistik; võimaldas kirjutada lühikesed ja konkreetsed
    GET ja POST teenused. Kuigi varem kasutanud ei olnud, oli väga intuitiivne ja kergesti omandatav.
* andmebaasiliides - ActiveJDBC
    ActiveRecordi analoog Javale, võimaldab runtime lähenemist klassidele vähese *boilerplate* koodiga.
    Kuigi varem kasutanud ei olnud, oli võrdlemisi intuitiivne, sest kokkupuude ActiveRecordiga on olnud hobiprojekti
    raames tihe.
* andmebaas - PostgreSQL
    Isiklik eelistus, kuna antud rakenduse puhul poleks erilist vahet olnud pea ühegi baasiserveri puhul.
    Olen hiljuti hobiprojekti puhul just Postgre'd kasutanud, mistap see ol parajasti olemas ja käe järgi.
* JSON - javax.json
    Varasemalt kasutatud, seega tuttav.
* testid - testng
    Varasemalt kasutatud.

