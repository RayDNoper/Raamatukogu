
CREATE SEQUENCE autor_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE autorlus_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE autor_teos (
    id integer DEFAULT nextval('autorlus_id_seq'::regclass) NOT NULL,
    autor_id integer NOT NULL,
    teos_id integer NOT NULL
);

CREATE TABLE autorid (
    id integer DEFAULT nextval('autor_id_seq'::regclass) NOT NULL,
    nimi character varying(200) NOT NULL,
    synniaeg date,
    surmaaeg date
);

CREATE SEQUENCE laenutus_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE laenutused (
    id integer DEFAULT nextval('laenutus_id_seq'::regclass) NOT NULL,
    lugeja_id integer NOT NULL,
    teos_id integer NOT NULL,
    algus timestamp without time zone,
    lopp timestamp without time zone
);

CREATE SEQUENCE lugeja_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE lugejad (
    id integer DEFAULT nextval('lugeja_id_seq'::regclass) NOT NULL,
    nimi character varying(200) NOT NULL,
    synniaeg date
);

CREATE SEQUENCE teos_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE teosed (
    id integer DEFAULT nextval('teos_id_seq'::regclass) NOT NULL,
    pealkiri character varying(200) NOT NULL,
    aasta integer
);

ALTER TABLE ONLY autor_teos
    ADD CONSTRAINT autor_teos_pkey PRIMARY KEY (id);


ALTER TABLE ONLY autorid
    ADD CONSTRAINT autorid_pkey PRIMARY KEY (id);

ALTER TABLE ONLY laenutused
    ADD CONSTRAINT laenutus_pkey PRIMARY KEY (id);

ALTER TABLE ONLY lugejad
    ADD CONSTRAINT lugejad_pkey PRIMARY KEY (id);

ALTER TABLE ONLY teosed
    ADD CONSTRAINT teosed_pkey PRIMARY KEY (id);


--
-- Name: autor_teos_autor_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY autor_teos
    ADD CONSTRAINT autor_teos_autor_id_fkey FOREIGN KEY (autor_id) REFERENCES autorid(id);


--
-- Name: autor_teos_teos_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY autor_teos
    ADD CONSTRAINT autor_teos_teos_id_fkey FOREIGN KEY (teos_id) REFERENCES teosed(id);


--
-- Name: laenutus_lugeja_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY laenutused
    ADD CONSTRAINT laenutus_lugeja_id_fkey FOREIGN KEY (lugeja_id) REFERENCES lugejad(id);


--
-- Name: laenutus_teos_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY laenutused
    ADD CONSTRAINT laenutus_teos_id_fkey FOREIGN KEY (teos_id) REFERENCES teosed(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- Name: autor_id_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE autor_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE autor_id_seq FROM postgres;
GRANT ALL ON SEQUENCE autor_id_seq TO postgres;
GRANT ALL ON SEQUENCE autor_id_seq TO raamatukogu;


--
-- Name: autorlus_id_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE autorlus_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE autorlus_id_seq FROM postgres;
GRANT ALL ON SEQUENCE autorlus_id_seq TO postgres;
GRANT ALL ON SEQUENCE autorlus_id_seq TO raamatukogu;


--
-- Name: autor_teos; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE autor_teos FROM PUBLIC;
REVOKE ALL ON TABLE autor_teos FROM postgres;
GRANT ALL ON TABLE autor_teos TO postgres;
GRANT ALL ON TABLE autor_teos TO raamatukogu;


--
-- Name: autorid; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE autorid FROM PUBLIC;
REVOKE ALL ON TABLE autorid FROM postgres;
GRANT ALL ON TABLE autorid TO postgres;
GRANT ALL ON TABLE autorid TO raamatukogu;


--
-- Name: laenutus_id_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE laenutus_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE laenutus_id_seq FROM postgres;
GRANT ALL ON SEQUENCE laenutus_id_seq TO postgres;
GRANT ALL ON SEQUENCE laenutus_id_seq TO raamatukogu;


--
-- Name: laenutused; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE laenutused FROM PUBLIC;
REVOKE ALL ON TABLE laenutused FROM postgres;
GRANT ALL ON TABLE laenutused TO postgres;
GRANT ALL ON TABLE laenutused TO raamatukogu;


--
-- Name: lugeja_id_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE lugeja_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE lugeja_id_seq FROM postgres;
GRANT ALL ON SEQUENCE lugeja_id_seq TO postgres;
GRANT ALL ON SEQUENCE lugeja_id_seq TO raamatukogu;


--
-- Name: lugejad; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE lugejad FROM PUBLIC;
REVOKE ALL ON TABLE lugejad FROM postgres;
GRANT ALL ON TABLE lugejad TO postgres;
GRANT ALL ON TABLE lugejad TO raamatukogu;


--
-- Name: teos_id_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE teos_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE teos_id_seq FROM postgres;
GRANT ALL ON SEQUENCE teos_id_seq TO postgres;
GRANT ALL ON SEQUENCE teos_id_seq TO raamatukogu;


--
-- Name: teosed; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE teosed FROM PUBLIC;
REVOKE ALL ON TABLE teosed FROM postgres;
GRANT ALL ON TABLE teosed TO postgres;
GRANT ALL ON TABLE teosed TO raamatukogu;


--
-- PostgreSQL database dump complete
--

