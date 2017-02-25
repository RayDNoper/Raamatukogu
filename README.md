Palun loo väike rakendus, kus on raamatukogu funktsionaalsus (kataloog, laenutus ja tagastamine). 
Rakendus koosneb kahest osast – UI ja server. Server peab pakkuma andmeid JSON formaadis üle REST endpointide. 
UI võib asuda ka serveri komponendi sees, aga peab kasutama andmete kuvamiseks ainult vastavaid REST teenuseid. 
REST teenused peavad olema kaetud integratsioonitestidega ning omama healthchecki. 
Rakenduse esitlemisel tuleb põhjendada valitud tehnoloogiaid ja loodud lahendust, sh vajadusel ka koodi selgitama.

SCHEMA
Raamat  (id, pealkiri, aasta)
Autor	(id, nimi, synniaasta, surmaaasta)
Autorlus (id, raamat_id, lugeja_id)
Lugeja 	(id, nimi)
Laenutus (id, raamat_id, lugeja_id)


API

/raamatukogu
	/kataloog
		GET (filter -> kataloog)
	/laenutus
		POST (teos, lugeja -> laenutus)
	/tagastus
		POST (teos, lugeja -> laenutus)
	/laenutused
		GET (lugeja -> laenutused)