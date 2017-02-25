--
-- Name: autor_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('autor_id_seq', 23, true);


--
-- Data for Name: autorid; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY autorid (id, nimi, synniaeg, surmaaeg) FROM stdin;
4	Isaac Asimov	\N	\N
5	Arthur C. Clarke	\N	\N
10	Clifford D. Simak	\N	\N
11	Robert A. Heinlein	\N	\N
16	Poul Anderson	\N	\N
22	Ray Bradbury	\N	\N
\.


--
-- Data for Name: teosed; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY teosed (id, pealkiri, aasta) FROM stdin;
27	Foundation and Earth	1986
28	Childhood`s End	1953
29	Rendezvous with Rama	1973
30	Foundation	1951
31	Foundation and Empire	1952
32	Prelude to Foundation	1988
33	City	1981
34	Orphans of the Sky	1959
35	Magic, Inc.	1940
36	The Puppet Masters	1951
37	The Door into Summer	1957
38	Double Star	1956
39	The High Crusade	1960
40	The City and the Stars	1956
41	The Goblin Reservation	1968
42	Foundation`s Edge	1982
43	Second Foundation	1953
44	Forward the Foundation	1993
45	The Illustrated Man	1951
46	Fahrenheit 451	1953
\.


--
-- Data for Name: autor_teos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY autor_teos (id, autor_id, teos_id) FROM stdin;
4	4	27
5	5	28
10	10	33
11	11	34
16	16	39
22	22	45
7	4	30
8	4	31
9	4	32
19	4	42
20	4	43
21	4	44
18	10	41
12	11	35
13	11	36
14	11	37
15	11	38
23	22	46
6	5	29
17	5	40
\.


--
-- Name: autorlus_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('autorlus_id_seq', 23, true);


--
-- Name: laenutus_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('laenutus_id_seq', 2, true);


--
-- Data for Name: lugejad; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY lugejad (id, nimi, synniaeg) FROM stdin;
1	Jaan Tester	1981-01-01
2	Juhan Tester	1982-02-02
3	Mari Tester	1983-03-03
\.


--
-- Data for Name: laenutused; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY laenutused (id, lugeja_id, teos_id, algus, lopp) FROM stdin;
1	2	27	2017-02-25 00:00:00	\N
2	2	45	2017-02-25 00:00:00	\N
\.


--
-- Name: lugeja_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('lugeja_id_seq', 3, true);


--
-- Name: teos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('teos_id_seq', 46, true);


--
-- PostgreSQL database dump complete
--

