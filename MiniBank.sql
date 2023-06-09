--
-- PostgreSQL database dump
--

-- Dumped from database version 15.2
-- Dumped by pg_dump version 15.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: job_enum; Type: TYPE; Schema: public; Owner: bintang.marsyuma.bm
--

CREATE TYPE public.job_enum AS ENUM (
    'pelajar',
    'pns',
    'wiraswasta',
    'pengangguran'
);


ALTER TYPE public.job_enum OWNER TO "bintang.marsyuma.bm";

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: admin; Type: TABLE; Schema: public; Owner: bintang.marsyuma.bm
--

CREATE TABLE public.admin (
    id integer NOT NULL,
    username character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    role_id integer DEFAULT 1
);


ALTER TABLE public.admin OWNER TO "bintang.marsyuma.bm";

--
-- Name: admin_id_seq; Type: SEQUENCE; Schema: public; Owner: bintang.marsyuma.bm
--

CREATE SEQUENCE public.admin_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.admin_id_seq OWNER TO "bintang.marsyuma.bm";

--
-- Name: admin_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: bintang.marsyuma.bm
--

ALTER SEQUENCE public.admin_id_seq OWNED BY public.admin.id;


--
-- Name: nasabah; Type: TABLE; Schema: public; Owner: bintang.marsyuma.bm
--

CREATE TABLE public.nasabah (
    user_id bigint NOT NULL,
    name character varying(50) NOT NULL,
    address text NOT NULL,
    phonenumber character varying(15) NOT NULL,
    balance bigint DEFAULT 0,
    email character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    role_id integer DEFAULT 2,
    username character varying(255) NOT NULL,
    job character varying(255)
);


ALTER TABLE public.nasabah OWNER TO "bintang.marsyuma.bm";

--
-- Name: role; Type: TABLE; Schema: public; Owner: bintang.marsyuma.bm
--

CREATE TABLE public.role (
    id integer NOT NULL,
    name character varying(50) NOT NULL
);


ALTER TABLE public.role OWNER TO "bintang.marsyuma.bm";

--
-- Name: role_id_seq; Type: SEQUENCE; Schema: public; Owner: bintang.marsyuma.bm
--

CREATE SEQUENCE public.role_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.role_id_seq OWNER TO "bintang.marsyuma.bm";

--
-- Name: role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: bintang.marsyuma.bm
--

ALTER SEQUENCE public.role_id_seq OWNED BY public.role.id;


--
-- Name: transactions; Type: TABLE; Schema: public; Owner: bintang.marsyuma.bm
--

CREATE TABLE public.transactions (
    transaction_id integer NOT NULL,
    sender_id bigint,
    recipient_id bigint,
    amount bigint NOT NULL,
    transaction_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    transaction_type integer
);


ALTER TABLE public.transactions OWNER TO "bintang.marsyuma.bm";

--
-- Name: transactions_transaction_id_seq; Type: SEQUENCE; Schema: public; Owner: bintang.marsyuma.bm
--

CREATE SEQUENCE public.transactions_transaction_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.transactions_transaction_id_seq OWNER TO "bintang.marsyuma.bm";

--
-- Name: transactions_transaction_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: bintang.marsyuma.bm
--

ALTER SEQUENCE public.transactions_transaction_id_seq OWNED BY public.transactions.transaction_id;


--
-- Name: type; Type: TABLE; Schema: public; Owner: bintang.marsyuma.bm
--

CREATE TABLE public.type (
    type_id integer NOT NULL,
    transaction character varying(255) NOT NULL
);


ALTER TABLE public.type OWNER TO "bintang.marsyuma.bm";

--
-- Name: type_type_id_seq; Type: SEQUENCE; Schema: public; Owner: bintang.marsyuma.bm
--

CREATE SEQUENCE public.type_type_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.type_type_id_seq OWNER TO "bintang.marsyuma.bm";

--
-- Name: type_type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: bintang.marsyuma.bm
--

ALTER SEQUENCE public.type_type_id_seq OWNED BY public.type.type_id;


--
-- Name: admin id; Type: DEFAULT; Schema: public; Owner: bintang.marsyuma.bm
--

ALTER TABLE ONLY public.admin ALTER COLUMN id SET DEFAULT nextval('public.admin_id_seq'::regclass);


--
-- Name: role id; Type: DEFAULT; Schema: public; Owner: bintang.marsyuma.bm
--

ALTER TABLE ONLY public.role ALTER COLUMN id SET DEFAULT nextval('public.role_id_seq'::regclass);


--
-- Name: transactions transaction_id; Type: DEFAULT; Schema: public; Owner: bintang.marsyuma.bm
--

ALTER TABLE ONLY public.transactions ALTER COLUMN transaction_id SET DEFAULT nextval('public.transactions_transaction_id_seq'::regclass);


--
-- Name: type type_id; Type: DEFAULT; Schema: public; Owner: bintang.marsyuma.bm
--

ALTER TABLE ONLY public.type ALTER COLUMN type_id SET DEFAULT nextval('public.type_type_id_seq'::regclass);


--
-- Data for Name: admin; Type: TABLE DATA; Schema: public; Owner: bintang.marsyuma.bm
--

COPY public.admin (id, username, password, role_id) FROM stdin;
1	admin1	password1	1
2	admin2	password2	1
3	admin3	password3	1
4	bintang	1234	1
\.


--
-- Data for Name: nasabah; Type: TABLE DATA; Schema: public; Owner: bintang.marsyuma.bm
--

COPY public.nasabah (user_id, name, address, phonenumber, balance, email, password, role_id, username, job) FROM stdin;
8	denise armadilo	jalan depok	081234567892	500000	email8@example.com	1234	2	username8	pns
1	Yasmina Ashfa	Jalan Raya 123	081234567890	913695	email1@example.com	1234	2	username1	pelajar
5	Bisma Alif	Lampung Utara	081252658596	245574	email5@example.com	1234	2	username5	pelajar
7	rafie armadilo	jalan depok	081234567891	602575	email7@example.com	1234	2	username7	pengangguran
2	Bintang Marsyuma	Jalan Merdeka 456	082345678901	10005000	email2@example.com	1234	2	username2	wiraswasta
3	Ahmad Abdullah	Jalan Kenangan 789	083456789012	25005000	email3@example.com	1234	2	username3	pns
4	Rina Permatasari	Jalan Sudirman 789	084567890123	14950000	email4@example.com	1234	2	username4	wiraswasta
555	akbar	jepara	081685695	175000	akbar@ui.ac.id	akbargans	2	TNI	AKbarGG
1005	ilham	Majapahit	081296015003	119696	ilham@gmail.com	1234abc	2	Nganggur	ilhamgod
9	bubin	jalan depok	081234567824	4264661	9@gmail.com	1234	2	username9	pns
6	Rizki	jakarta Utara	3243224234	113436	email6@example.com	1234	2	username6	wiraswasta
1006	ilham	Majapahit	081296015006	-4696	ilham@gmail.coms	1234abcs	2	ilhamgods	Nganggur
\.


--
-- Data for Name: role; Type: TABLE DATA; Schema: public; Owner: bintang.marsyuma.bm
--

COPY public.role (id, name) FROM stdin;
1	admin
2	nasabah
\.


--
-- Data for Name: transactions; Type: TABLE DATA; Schema: public; Owner: bintang.marsyuma.bm
--

COPY public.transactions (transaction_id, sender_id, recipient_id, amount, transaction_date, transaction_type) FROM stdin;
22	4	5	50000	2023-06-13 09:38:18.157967	1
23	\N	5	10000	2023-06-13 10:28:20.954448	2
24	1	5	15050	2023-06-17 04:38:54.845724	1
25	1	5	15250	2023-06-17 04:39:37.696868	1
26	6	7	89975	2023-06-17 04:42:08.644221	1
27	6	7	87600	2023-06-17 04:43:57.152983	1
28	1006	1005	69696	2023-06-18 10:35:15.514749	1
29	9	\N	555555	2023-06-18 11:40:58.444371	3
30	9	\N	89892	2023-06-18 11:42:23.056169	3
31	9	\N	89892	2023-06-18 11:42:33.850043	3
32	6	\N	8989	2023-06-18 11:44:53.077586	3
33	\N	1006	15000	2023-06-18 12:18:36.434144	2
\.


--
-- Data for Name: type; Type: TABLE DATA; Schema: public; Owner: bintang.marsyuma.bm
--

COPY public.type (type_id, transaction) FROM stdin;
1	transfer
2	deposit
3	withdraw
\.


--
-- Name: admin_id_seq; Type: SEQUENCE SET; Schema: public; Owner: bintang.marsyuma.bm
--

SELECT pg_catalog.setval('public.admin_id_seq', 4, true);


--
-- Name: role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: bintang.marsyuma.bm
--

SELECT pg_catalog.setval('public.role_id_seq', 2, true);


--
-- Name: transactions_transaction_id_seq; Type: SEQUENCE SET; Schema: public; Owner: bintang.marsyuma.bm
--

SELECT pg_catalog.setval('public.transactions_transaction_id_seq', 33, true);


--
-- Name: type_type_id_seq; Type: SEQUENCE SET; Schema: public; Owner: bintang.marsyuma.bm
--

SELECT pg_catalog.setval('public.type_type_id_seq', 3, true);


--
-- Name: admin admin_pkey; Type: CONSTRAINT; Schema: public; Owner: bintang.marsyuma.bm
--

ALTER TABLE ONLY public.admin
    ADD CONSTRAINT admin_pkey PRIMARY KEY (id);


--
-- Name: nasabah nasabah_phonenumber_key1; Type: CONSTRAINT; Schema: public; Owner: bintang.marsyuma.bm
--

ALTER TABLE ONLY public.nasabah
    ADD CONSTRAINT nasabah_phonenumber_key1 UNIQUE (phonenumber);


--
-- Name: nasabah nasabah_pkey1; Type: CONSTRAINT; Schema: public; Owner: bintang.marsyuma.bm
--

ALTER TABLE ONLY public.nasabah
    ADD CONSTRAINT nasabah_pkey1 PRIMARY KEY (user_id);


--
-- Name: nasabah nasabah_username_key; Type: CONSTRAINT; Schema: public; Owner: bintang.marsyuma.bm
--

ALTER TABLE ONLY public.nasabah
    ADD CONSTRAINT nasabah_username_key UNIQUE (username);


--
-- Name: role role_pkey; Type: CONSTRAINT; Schema: public; Owner: bintang.marsyuma.bm
--

ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);


--
-- Name: transactions transactions_pkey; Type: CONSTRAINT; Schema: public; Owner: bintang.marsyuma.bm
--

ALTER TABLE ONLY public.transactions
    ADD CONSTRAINT transactions_pkey PRIMARY KEY (transaction_id);


--
-- Name: type type_pkey; Type: CONSTRAINT; Schema: public; Owner: bintang.marsyuma.bm
--

ALTER TABLE ONLY public.type
    ADD CONSTRAINT type_pkey PRIMARY KEY (type_id);


--
-- Name: nasabah unique_email; Type: CONSTRAINT; Schema: public; Owner: bintang.marsyuma.bm
--

ALTER TABLE ONLY public.nasabah
    ADD CONSTRAINT unique_email UNIQUE (email);


--
-- Name: nasabah unique_username; Type: CONSTRAINT; Schema: public; Owner: bintang.marsyuma.bm
--

ALTER TABLE ONLY public.nasabah
    ADD CONSTRAINT unique_username UNIQUE (username);


--
-- Name: transactions transactions_transaction_type_fkey; Type: FK CONSTRAINT; Schema: public; Owner: bintang.marsyuma.bm
--

ALTER TABLE ONLY public.transactions
    ADD CONSTRAINT transactions_transaction_type_fkey FOREIGN KEY (transaction_type) REFERENCES public.type(type_id);


--
-- PostgreSQL database dump complete
--

