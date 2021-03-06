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
    Autorlus (id, raamat_id, autor_id)
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
---
Kuna antud projekt on suhteliselt minimalistlik prototüüp, sai valitud teegid eelkõige selle järgi, et koodi tuleks
kirjutada võimalikult vähe, samas, et see oleks võrdlemisi loetav ka ilma teekide käitumisse süvenemata.

* REST - Spark

    Minimalistlik, ilma suurema *overhead*ita veebirakenduste raamistik; võimaldas kirjutada lühikesed ja konkreetsed
    GET ja POST teenused. Kuigi varem kasutanud ei olnud, oli väga intuitiivne ja kergesti omandatav.
* andmebaasiliides - ActiveJDBC

    ActiveRecordi analoog Javale, võimaldab runtime lähenemist klassidele vähese *boilerplate* koodiga.
    Kuigi varem kasutanud ei olnud, oli võrdlemisi intuitiivne, sest kokkupuude ActiveRecordiga on olnud hobiprojekti
    raames tihe.
* andmebaas - PostgreSQL

    Isiklik eelistus, kuna antud rakenduse kontekstis poleks erilist vahet olnud pea ühegi baasiserveri puhul.
    Olen hiljuti hobiprojekti jaoks just Postgre'd kasutanud, mistap see oli parajasti olemas ja käe järgi.
* JSON - javax.json

    Varasemalt kasutatud, seega tuttav.
* testid - testng

    Varasemalt kasutatud.
* klientprogramm - jQueryUI ja selle teegid jsGrid ja jsTabs

    jQuery(UI) peetakse küll aegnunuks ja ülemäära suureks, kuid on erinevalt paljudest teistest js teekidest stabiilne
    ja (võrdlemisi) hästi dokumenteeritud, mistõttu on seda hea kasutada väikeste prototüüp-kasutajaliideste tegemiseks.

Lahenduse lühikirjeldus
---
* Spark server genereerib vastavalt defineeritud REST API-le neli teenust - kataloog, laenutuste loetelu, laenuta ja
    tagasta.
* teenused ühenduvad andmebaasiga üle ActiveJDBC mudelite.
* JS/HTML veebiliides teeb jQuery GET päringud REST API vastu ja kuvab saadu tabelis, mille read avavad omakorda jQuery
    dialoogiakna, mille kaudu saab kasutaja valitud teose kas laenutada või tagastada.

Kasutamine
---
* Teenust saab käivitada *standalone*-teenusena, siis paneb sisseehitatud Jetty selle tööle localhost:4567 külge.
  Vajadusel saab seda käivitada ka mõne veebiteenuse rakendusena.
* Raamatukogu.html sisaldab lõiku
    // KONSTANDID
    var service = "http://127.0.0.1:4567";
  milles olevat aadressi tuleb muuta, kui teenust jooksutatakse mõnel muul aadressil.
* Kasutajaliides käivitub Raamatukogu.html-ist, tegu on ühe-lehe HTML/js lahendusega.

Lisainfo
---
Projekti test-andmed on võetud hobiprojektist, milleks on Eesti ulmehuviliste kollektiivne lugemispäevik "Ulmekirjanduse
BAAS" (hetke arendusversioon asub aadressil http://uusbaas.ulme.ee ). Antud veebileht oli esialgu kirjutatud 1990ndatel Access/VB.NET rakendusena, kuid seda jooksutav server lõpetas
2016. sügisel lõplikult töö. Access andmebaas sai konverteeritud Postgres'i, olemasolev kujundus võimalikult palju
säilitatud ja uus teenus kirjutatud, kasutades Rubyt, veebirakenduse raamistikuks Sinatrat ja andmebaasiraamistikuks
ActiveRecord'it. BAASi andmetest sai ümber tõstetud ~20 esimest teost koos autoritega.

Tööks kulunud aeg
---
(kõik ajad mõõduka mõõteveaga +/- 1h)

* planeerimine ja teekide valimine

    6h - kuna varasem kokkupuude REST-teenustega Javas on olnud *in-house* lahendustega, sai uurida ja tutvuda erinevate
    võimalustega ja katsetada paari laialt soovitatud raamistikku.

* baasimudelid ja REST teenus

    8h - jällegi, varasem kogemus on Javas Hibernate'ga, mis on tohutu *overhead*-iga ja väga tundlik erinevate
    piirsituatsioonide suhtes.

* Javascript/HTML kasutajaliides

    6h

* dokumenteerimine, sealhulgas Markupi uurimine

    2h

kokku: 22h
