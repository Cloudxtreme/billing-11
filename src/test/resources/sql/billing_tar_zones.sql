--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = true;

--
-- Name: tariff_zones; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tariff_zones (
    id integer NOT NULL,
    zone_id integer NOT NULL,
    zone_name character varying(30) NOT NULL,
    additional_kode character varying(10) DEFAULT '*'::character varying,
    dollar boolean NOT NULL,
    pref_profile integer DEFAULT 0 NOT NULL,
    tarif real NOT NULL,
    tarif_pref real
);
ALTER TABLE ONLY tariff_zones ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY tariff_zones ALTER COLUMN zone_id SET STATISTICS 0;
ALTER TABLE ONLY tariff_zones ALTER COLUMN zone_name SET STATISTICS 0;
ALTER TABLE ONLY tariff_zones ALTER COLUMN additional_kode SET STATISTICS 0;
ALTER TABLE ONLY tariff_zones ALTER COLUMN dollar SET STATISTICS 0;
ALTER TABLE ONLY tariff_zones ALTER COLUMN pref_profile SET STATISTICS 0;
ALTER TABLE ONLY tariff_zones ALTER COLUMN tarif SET STATISTICS 0;
ALTER TABLE ONLY tariff_zones ALTER COLUMN tarif_pref SET STATISTICS 0;


ALTER TABLE public.tariff_zones OWNER TO postgres;

--
-- Name: tariff_zones_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tariff_zones_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tariff_zones_id_seq OWNER TO postgres;

--
-- Name: tariff_zones_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE tariff_zones_id_seq OWNED BY tariff_zones.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tariff_zones ALTER COLUMN id SET DEFAULT nextval('tariff_zones_id_seq'::regclass);


--
-- Data for Name: tariff_zones; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tariff_zones (id, zone_id, zone_name, additional_kode, dollar, pref_profile, tarif, tarif_pref) FROM stdin;
111	58	Аудиотекс_0900265	*	f	2	1.16700006	\N
112	59	Аудиотекс_0900266	*	f	2	1.66700006	\N
113	60	Аудиотекс_0900267	*	f	2	5.83400011	\N
114	61	Аудиотекс_0900268	*	f	2	2.5	\N
115	62	Аудиотекс_0900269	*	f	2	4.16699982	\N
117	64	Россия Алт.(остальное)	*	f	1	0.0276999995	0.0208000001
116	63	Россия(Мск_Спб кроме моб) Алт.	*	f	1	0.0125000002	0.00970000029
118	65	Страны Снг Алт.	*	f	1	0.0276999995	0.0208000001
119	66	Европа, Турция, Израиль Алт.	*	f	1	0.0346999988	0.0276999995
120	67	США, Канада Алт.	*	f	1	0.0346999988	0.0276999995
31	18	Сеть Велтон Телеком 0-700-10	*	f	2	0.0132999998	\N
34	17	Сеть спутн.связи 0088, 0087	87039	t	2	0.697700024	\N
63	17	Сеть спутн.связи 0088, 0087	87076	t	2	0.174999997	\N
64	17	Сеть спутн.связи 0088, 0087	8816	t	2	0.216700003	\N
66	17	Сеть спутн.связи 0088, 0087	88213	t	2	0.216700003	\N
67	17	Сеть спутн.связи 0088, 0087	8705	t	2	0.223299995	\N
68	17	Сеть спутн.связи 0088, 0087	8817	t	2	0.223299995	\N
69	17	Сеть спутн.связи 0088, 0087	87030	t	2	0.2333	\N
70	17	Сеть спутн.связи 0088, 0087	8711	t	2	0.333299994	\N
76	23	Аудиотекс_0900102	*	f	2	3	\N
27	14	Другие страны	*	t	1	0.0250000004	0.0208000001
77	24	Аудиотекс_0900100	*	f	2	0	\N
78	25	Аудиотекс_0900101	*	f	2	0.5	\N
79	26	Аудиотекс_0900103	*	f	2	0.800000012	\N
80	27	Аудиотекс_0900104	*	f	2	1.35000002	\N
81	28	Аудиотекс_0900105	*	f	2	1.64999998	\N
82	29	Аудиотекс_0900106	*	f	2	2	\N
83	30	Аудиотекс_0900107	*	f	2	2.5	\N
2	2	Украина	*	f	3	0.00832999963	0.00832999963
1	1	Украина. Мобильные операторы	*	f	4	0.0166999996	0.0166999996
3	3	Одесская область	*	f	3	0.00832999963	0
4	4	В пределах СНГ	*	t	5	0.00469999993	0.0037
18	5	Восточная Европа моб	*	t	6	0.0092000002	0.00749999983
19	6	Восточная Европа	*	t	7	0.00749999983	0.00579999993
23	10	Северная Америка	*	t	8	0.00499999989	0.00400000019
20	7	Центральная и Северная Европа	*	t	9	0.00829999987	0.00669999979
21	8	Западная Европа	*	t	9	0.00829999987	0.00669999979
22	9	Центр.Азия, Близ.восток	*	t	9	0.00829999987	0.00669999979
24	11	Восточная Азия	*	t	9	0.00829999987	0.00669999979
25	12	Афр., Юж. и Центр. Амер.	*	t	9	0.00829999987	0.00669999979
26	13	Австралия, Океания	*	t	9	0.00829999987	0.00669999979
71	19	Росc, Белорусc, Молд	*	t	5	0.00469999993	0.0037
72	20	Азерб, Армен, Грузия	*	t	10	0.00630000001	0.00499999989
73	21	Тадж, Туркм, Казх, Кирг, Узбек	*	t	11	0.00669999979	0.00529999984
74	22	Сети моб операторов СНГ	*	t	11	0.00669999979	0.00529999984
29	16	0-800 Бесплатная услуга	*	f	12	0	0
52	17	Сеть спутн.связи 0088, 0087	88232	t	13	0.0599999987	0.0599999987
51	17	Сеть спутн.связи 0088, 0087	88216	t	13	0.0599999987	0.0599999987
50	17	Сеть спутн.связи 0088, 0087	88298	t	13	0.0599999987	0.0599999987
49	17	Сеть спутн.связи 0088, 0087	88213	t	13	0.0599999987	0.0599999987
48	17	Сеть спутн.связи 0088, 0087	88213	t	13	0.0599999987	0.0599999987
30	17	Сеть спутн.связи 0088, 0087	87077	t	13	0.0599999987	0.0599999987
32	17	Сеть спутн.связи 0088, 0087	88213	t	13	0.0599999987	0.0599999987
36	17	Сеть спутн.связи 0088, 0087	88233	t	13	0.0599999987	0.0599999987
38	17	Сеть спутн.связи 0088, 0087	8812	t	13	0.0599999987	0.0599999987
45	17	Сеть спутн.связи 0088, 0087	8717	t	13	0.0599999987	0.0599999987
46	17	Сеть спутн.связи 0088, 0087	87160	t	13	0.0599999987	0.0599999987
53	17	Сеть спутн.связи 0088, 0087	8812	t	13	0.0599999987	0.0599999987
54	17	Сеть спутн.связи 0088, 0087	8717	t	13	0.0599999987	0.0599999987
55	17	Сеть спутн.связи 0088, 0087	87160	t	13	0.0599999987	0.0599999987
60	17	Сеть спутн.связи 0088, 0087	88216	t	13	0.0599999987	0.0599999987
56	17	Сеть спутн.связи 0088, 0087	88232	t	13	0.0599999987	0.0599999987
58	17	Сеть спутн.связи 0088, 0087	88298	t	13	0.0599999987	0.0599999987
33	17	Сеть спутн.связи 0088, 0087	88234	t	14	0.0900000036	0.0900000036
35	17	Сеть спутн.связи 0088, 0087	8817	t	14	0.0900000036	0.0900000036
84	31	Аудиотекс_0900108	*	f	2	5	\N
37	17	Сеть спутн.связи 0088, 0087	87078	t	14	0.0900000036	0.0900000036
47	17	Сеть спутн.связи 0088, 0087	8816	t	14	0.0900000036	0.0900000036
62	17	Сеть спутн.связи 0088, 0087	8810	t	14	0.0900000036	0.0900000036
89	36	Аудиотекс_0900303	*	f	2	0.75	\N
88	35	Аудиотекс_0900302	*	f	2	1	\N
87	34	Аудиотекс_0900301	*	f	2	1.29499996	\N
86	33	Аудиотекс_0900300	*	f	2	0	\N
90	37	Аудиотекс_0900304	*	f	2	0.666999996	\N
92	39	Аудиотекс_0900306	*	f	2	0.333999991	\N
93	40	Аудиотекс_0900307	*	f	2	5.83400011	\N
95	42	Аудиотекс_0900309	*	f	2	2.5	\N
96	43	Аудиотекс_0900450	*	f	2	0.25	\N
97	44	Аудиотекс_0900451	*	f	2	0.333999991	\N
98	45	Аудиотекс_0900452	*	f	2	0.400000006	\N
99	46	Аудиотекс_0900453	*	f	2	0.550000012	\N
100	47	Аудиотекс_0900454	*	f	2	0.649999976	\N
101	48	Аудиотекс_0900455	*	f	2	0.833999991	\N
102	49	Аудиотекс_0900456	*	f	2	1	\N
103	50	Аудиотекс_0900457	*	f	2	1.25	\N
104	51	Аудиотекс_0900458	*	f	2	1.66700006	\N
105	52	Аудиотекс_0900459	*	f	2	0	\N
106	53	Аудиотекс_0900260	*	f	2	7.5	\N
107	54	Аудиотекс_0900261	*	f	2	0.26699999	\N
108	55	Аудиотекс_0900262	*	f	2	0.100000001	\N
109	56	Аудиотекс_0900263	*	f	2	0.833999991	\N
110	57	Аудиотекс_0900264	*	f	2	10	\N
61	17	Сеть спутн.связи 0088, 0087	88234	t	14	0.0900000036	0.0900000036
39	17	Сеть спутн.связи 0088, 0087	8711	t	14	0.0900000036	0.0900000036
40	17	Сеть спутн.связи 0088, 0087	8701	t	14	0.0900000036	0.0900000036
41	17	Сеть спутн.связи 0088, 0087	8715	t	14	0.0900000036	0.0900000036
42	17	Сеть спутн.связи 0088, 0087	8703	t	14	0.0900000036	0.0900000036
43	17	Сеть спутн.связи 0088, 0087	87039	t	14	0.0900000036	0.0900000036
44	17	Сеть спутн.связи 0088, 0087	8706	t	14	0.0900000036	0.0900000036
28	15	Моб.кроме Вост.Европы	*	t	15	0.0108000003	0.0092000002
91	38	Аудиотекс_0900305	*	f	16	1.66700006	1.66700006
94	41	Аудиотекс_0900308	*	f	17	0.25	0.25
85	32	Аудиотекс_0900109	*	f	18	6.25	6.25
\.


--
-- Name: tariff_zones_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tariff_zones_id_seq', 120, true);


--
-- Name: tariff_zones_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tariff_zones
    ADD CONSTRAINT tariff_zones_pkey PRIMARY KEY (id);


--
-- Name: tariff_zones; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE tariff_zones FROM PUBLIC;
REVOKE ALL ON TABLE tariff_zones FROM postgres;
GRANT ALL ON TABLE tariff_zones TO postgres;
GRANT ALL ON TABLE tariff_zones TO bill_adm;


--
-- Name: tariff_zones_id_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE tariff_zones_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE tariff_zones_id_seq FROM postgres;
GRANT ALL ON SEQUENCE tariff_zones_id_seq TO postgres;
GRANT ALL ON SEQUENCE tariff_zones_id_seq TO bill_adm;


--
-- PostgreSQL database dump complete
--

