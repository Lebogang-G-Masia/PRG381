--
-- PostgreSQL database dump
--

-- Removed restrict

-- Dumped from database version 18.4
-- Dumped by pg_dump version 18.4

-- Started on 2026-07-21 00:48:47

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 226 (class 1259 OID 16684)
-- Name: cleaners; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cleaners (
    cleaner_id integer NOT NULL,
    first_name character varying(50) NOT NULL,
    last_name character varying(50) NOT NULL,
    phone character varying(20),
    email character varying(100),
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.cleaners OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 16683)
-- Name: cleaners_cleaner_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.cleaners_cleaner_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.cleaners_cleaner_id_seq OWNER TO postgres;

--
-- TOC entry 5085 (class 0 OID 0)
-- Dependencies: 225
-- Name: cleaners_cleaner_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cleaners_cleaner_id_seq OWNED BY public.cleaners.cleaner_id;


--
-- TOC entry 224 (class 1259 OID 16661)
-- Name: materials; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.materials (
    material_id integer NOT NULL,
    material_name character varying(100) NOT NULL,
    description text,
    quantity integer NOT NULL,
    reorder_level integer NOT NULL,
    unit character varying(20) NOT NULL,
    supplier_id integer NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT materials_quantity_check CHECK ((quantity >= 0)),
    CONSTRAINT materials_reorder_level_check CHECK ((reorder_level >= 0))
);


ALTER TABLE public.materials OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 16660)
-- Name: materials_material_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.materials_material_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.materials_material_id_seq OWNER TO postgres;

--
-- TOC entry 5086 (class 0 OID 0)
-- Dependencies: 223
-- Name: materials_material_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.materials_material_id_seq OWNED BY public.materials.material_id;


--
-- TOC entry 228 (class 1259 OID 16697)
-- Name: stock_issuance; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.stock_issuance (
    issuance_id integer NOT NULL,
    cleaner_id integer NOT NULL,
    issued_by integer NOT NULL,
    issue_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.stock_issuance OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 16718)
-- Name: stock_issuance_details; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.stock_issuance_details (
    detail_id integer NOT NULL,
    issuance_id integer NOT NULL,
    material_id integer NOT NULL,
    quantity integer NOT NULL,
    CONSTRAINT stock_issuance_details_quantity_check CHECK ((quantity > 0))
);


ALTER TABLE public.stock_issuance_details OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 16717)
-- Name: stock_issuance_details_detail_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.stock_issuance_details_detail_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.stock_issuance_details_detail_id_seq OWNER TO postgres;

--
-- TOC entry 5087 (class 0 OID 0)
-- Dependencies: 229
-- Name: stock_issuance_details_detail_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.stock_issuance_details_detail_id_seq OWNED BY public.stock_issuance_details.detail_id;


--
-- TOC entry 227 (class 1259 OID 16696)
-- Name: stock_issuance_issuance_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.stock_issuance_issuance_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.stock_issuance_issuance_id_seq OWNER TO postgres;

--
-- TOC entry 5088 (class 0 OID 0)
-- Dependencies: 227
-- Name: stock_issuance_issuance_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.stock_issuance_issuance_id_seq OWNED BY public.stock_issuance.issuance_id;


--
-- TOC entry 222 (class 1259 OID 16645)
-- Name: suppliers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.suppliers (
    supplier_id integer NOT NULL,
    company_name character varying(100) NOT NULL,
    contact_person character varying(100) NOT NULL,
    phone character varying(20) NOT NULL,
    email character varying(100),
    address text,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.suppliers OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16644)
-- Name: suppliers_supplier_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.suppliers_supplier_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.suppliers_supplier_id_seq OWNER TO postgres;

--
-- TOC entry 5089 (class 0 OID 0)
-- Dependencies: 221
-- Name: suppliers_supplier_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.suppliers_supplier_id_seq OWNED BY public.suppliers.supplier_id;


--
-- TOC entry 220 (class 1259 OID 16623)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    user_id integer NOT NULL,
    first_name character varying(50) NOT NULL,
    last_name character varying(50) NOT NULL,
    username character varying(50) NOT NULL,
    email character varying(100) NOT NULL,
    password character varying(255) NOT NULL,
    role character varying(20) DEFAULT 'Staff'::character varying NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16622)
-- Name: users_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_user_id_seq OWNER TO postgres;

--
-- TOC entry 5090 (class 0 OID 0)
-- Dependencies: 219
-- Name: users_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_user_id_seq OWNED BY public.users.user_id;


--
-- TOC entry 4888 (class 2604 OID 16687)
-- Name: cleaners cleaner_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cleaners ALTER COLUMN cleaner_id SET DEFAULT nextval('public.cleaners_cleaner_id_seq'::regclass);


--
-- TOC entry 4886 (class 2604 OID 16664)
-- Name: materials material_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.materials ALTER COLUMN material_id SET DEFAULT nextval('public.materials_material_id_seq'::regclass);


--
-- TOC entry 4890 (class 2604 OID 16700)
-- Name: stock_issuance issuance_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stock_issuance ALTER COLUMN issuance_id SET DEFAULT nextval('public.stock_issuance_issuance_id_seq'::regclass);


--
-- TOC entry 4892 (class 2604 OID 16721)
-- Name: stock_issuance_details detail_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stock_issuance_details ALTER COLUMN detail_id SET DEFAULT nextval('public.stock_issuance_details_detail_id_seq'::regclass);


--
-- TOC entry 4884 (class 2604 OID 16648)
-- Name: suppliers supplier_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.suppliers ALTER COLUMN supplier_id SET DEFAULT nextval('public.suppliers_supplier_id_seq'::regclass);


--
-- TOC entry 4881 (class 2604 OID 16626)
-- Name: users user_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN user_id SET DEFAULT nextval('public.users_user_id_seq'::regclass);


--
-- TOC entry 5075 (class 0 OID 16684)
-- Dependencies: 226
-- Data for Name: cleaners; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.cleaners (cleaner_id, first_name, last_name, phone, email, created_at) VALUES 
(1, 'John', 'Smith', '0821111111', 'john@cleaning.co.za', '2026-07-20 23:23:43.726687'),
(2, 'Sarah', 'Brown', '0822222222', 'sarah@cleaning.co.za', '2026-07-20 23:23:43.726687'),
(3, 'David', 'Jones', '0823333333', 'david@cleaning.co.za', '2026-07-20 23:23:43.726687');


--
-- TOC entry 5073 (class 0 OID 16661)
-- Dependencies: 224
-- Data for Name: materials; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.materials (material_id, material_name, description, quantity, reorder_level, unit, supplier_id, created_at) VALUES 
(2, 'Floor Mop', 'Cotton Floor Mop', 25, 5, 'Each', 2, '2026-07-20 23:25:14.659832'),
(3, 'Rubber Gloves', 'Medium Size', 100, 20, 'Pair', 3, '2026-07-20 23:25:14.659832'),
(4, 'Bucket', '20L Plastic Bucket', 30, 5, 'Each', 2, '2026-07-20 23:25:14.659832'),
(5, 'Glass Cleaner', '750ml Spray', 40, 10, 'Bottle', 1, '2026-07-20 23:25:14.659832'),
(1, 'Bleach', '5L Industrial Bleach', 45, 10, 'Bottle', 1, '2026-07-20 23:25:14.659832');


--
-- TOC entry 5077 (class 0 OID 16697)
-- Dependencies: 228
-- Data for Name: stock_issuance; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.stock_issuance (issuance_id, cleaner_id, issued_by, issue_date) VALUES 
(1, 1, 1, '2026-07-21 00:45:26.143739');


--
-- TOC entry 5079 (class 0 OID 16718)
-- Dependencies: 230
-- Data for Name: stock_issuance_details; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.stock_issuance_details (detail_id, issuance_id, material_id, quantity) VALUES 
(1, 1, 1, 5);


--
-- TOC entry 5071 (class 0 OID 16645)
-- Dependencies: 222
-- Data for Name: suppliers; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.suppliers (supplier_id, company_name, contact_person, phone, email, address, created_at) VALUES 
(1, 'Sparkle Supplies', 'Sarah Johnson', '011-555-1234', 'sarah@sparkle.co.za', '15 Main Road, Johannesburg', '2026-07-20 23:18:34.605479'),
(2, 'CleanPro Distributors', 'Michael Adams', '021-555-9876', 'michael@cleanpro.co.za', '22 Beach Road, Cape Town', '2026-07-20 23:18:34.605479'),
(3, 'Eco Cleaning Solutions', 'Amanda Smith', '031-555-4567', 'amanda@eco.co.za', '8 King Street, Durban', '2026-07-20 23:18:34.605479'),
(4, 'Sparkle Supplies', 'Sarah Johnson', '0115551234', 'sales@sparkle.co.za', '15 Main Road, Johannesburg', '2026-07-20 23:25:08.831905'),
(5, 'CleanPro', 'Mike Adams', '0215559876', 'info@cleanpro.co.za', '22 Beach Road, Cape Town', '2026-07-20 23:25:08.831905'),
(6, 'EcoClean', 'Amanda Smith', '0315554567', 'contact@ecoclean.co.za', '8 King Street, Durban', '2026-07-20 23:25:08.831905');


--
-- TOC entry 5069 (class 0 OID 16623)
-- Dependencies: 220
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.users (user_id, first_name, last_name, username, email, password, role, created_at) VALUES 
(1, 'Admin', 'User', 'admin', 'admin@cleaning.com', 'admin123', 'Admin', '2026-07-20 23:17:59.218478'),
(2, 'Masia', 'Member', 'Masia', 'masia@demo.com', 'masia123', 'Admin', '2026-07-20 23:17:59.218478'),
(3, 'Tshege', 'Member', 'Tshege', 'tshege@demo.com', 'tshege123', 'Admin', '2026-07-20 23:17:59.218478'),
(4, 'Dewald', 'Member', 'Dewald', 'dewald@demo.com', 'dewald123', 'Admin', '2026-07-20 23:17:59.218478'),
(5, 'Hendrik', 'Member', 'Hendrik', 'hendrik@demo.com', 'honi123', 'Admin', '2026-07-20 23:17:59.218478'),
(6, 'Zander', 'Member', 'Zander', 'zander@demo.com', 'zander123', 'Admin', '2026-07-20 23:17:59.218478');


--
-- TOC entry 5091 (class 0 OID 0)
-- Dependencies: 225
-- Name: cleaners_cleaner_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cleaners_cleaner_id_seq', 3, true);


--
-- TOC entry 5092 (class 0 OID 0)
-- Dependencies: 223
-- Name: materials_material_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.materials_material_id_seq', 5, true);


--
-- TOC entry 5093 (class 0 OID 0)
-- Dependencies: 229
-- Name: stock_issuance_details_detail_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.stock_issuance_details_detail_id_seq', 1, true);


--
-- TOC entry 5094 (class 0 OID 0)
-- Dependencies: 227
-- Name: stock_issuance_issuance_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.stock_issuance_issuance_id_seq', 1, true);


--
-- TOC entry 5095 (class 0 OID 0)
-- Dependencies: 221
-- Name: suppliers_supplier_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.suppliers_supplier_id_seq', 6, true);


--
-- TOC entry 5096 (class 0 OID 0)
-- Dependencies: 219
-- Name: users_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_user_id_seq', 6, true);


--
-- TOC entry 4909 (class 2606 OID 16695)
-- Name: cleaners cleaners_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cleaners
    ADD CONSTRAINT cleaners_email_key UNIQUE (email);


--
-- TOC entry 4911 (class 2606 OID 16693)
-- Name: cleaners cleaners_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cleaners
    ADD CONSTRAINT cleaners_pkey PRIMARY KEY (cleaner_id);


--
-- TOC entry 4907 (class 2606 OID 16677)
-- Name: materials materials_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.materials
    ADD CONSTRAINT materials_pkey PRIMARY KEY (material_id);


--
-- TOC entry 4915 (class 2606 OID 16728)
-- Name: stock_issuance_details stock_issuance_details_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stock_issuance_details
    ADD CONSTRAINT stock_issuance_details_pkey PRIMARY KEY (detail_id);


--
-- TOC entry 4913 (class 2606 OID 16706)
-- Name: stock_issuance stock_issuance_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stock_issuance
    ADD CONSTRAINT stock_issuance_pkey PRIMARY KEY (issuance_id);


--
-- TOC entry 4903 (class 2606 OID 16659)
-- Name: suppliers suppliers_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.suppliers
    ADD CONSTRAINT suppliers_email_key UNIQUE (email);


--
-- TOC entry 4905 (class 2606 OID 16657)
-- Name: suppliers suppliers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.suppliers
    ADD CONSTRAINT suppliers_pkey PRIMARY KEY (supplier_id);


--
-- TOC entry 4897 (class 2606 OID 16643)
-- Name: users users_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_email_key UNIQUE (email);


--
-- TOC entry 4899 (class 2606 OID 16639)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);


--
-- TOC entry 4901 (class 2606 OID 16641)
-- Name: users users_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_username_key UNIQUE (username);


--
-- TOC entry 4917 (class 2606 OID 16707)
-- Name: stock_issuance fk_cleaner; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stock_issuance
    ADD CONSTRAINT fk_cleaner FOREIGN KEY (cleaner_id) REFERENCES public.cleaners(cleaner_id);


--
-- TOC entry 4919 (class 2606 OID 16729)
-- Name: stock_issuance_details fk_issuance; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stock_issuance_details
    ADD CONSTRAINT fk_issuance FOREIGN KEY (issuance_id) REFERENCES public.stock_issuance(issuance_id) ON DELETE CASCADE;


--
-- TOC entry 4920 (class 2606 OID 16734)
-- Name: stock_issuance_details fk_material; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stock_issuance_details
    ADD CONSTRAINT fk_material FOREIGN KEY (material_id) REFERENCES public.materials(material_id);


--
-- TOC entry 4916 (class 2606 OID 16678)
-- Name: materials fk_supplier; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.materials
    ADD CONSTRAINT fk_supplier FOREIGN KEY (supplier_id) REFERENCES public.suppliers(supplier_id);


--
-- TOC entry 4918 (class 2606 OID 16712)
-- Name: stock_issuance fk_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stock_issuance
    ADD CONSTRAINT fk_user FOREIGN KEY (issued_by) REFERENCES public.users(user_id);


-- Completed on 2026-07-21 00:48:47

--
-- PostgreSQL database dump complete
--

-- Removed unrestrict

