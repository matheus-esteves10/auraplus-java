-- ============================================
-- TABELA: USERS
-- ============================================
CREATE TABLE T_ARP_USERS (
                             id SERIAL PRIMARY KEY,
                             nome VARCHAR(100) NOT NULL,
                             senha VARCHAR(255) NOT NULL,
                             email VARCHAR(150) UNIQUE NOT NULL,
                             role VARCHAR(50) NOT NULL,
                             cargo VARCHAR(100),
                             data_admissao TIMESTAMP,
                             ativo BOOLEAN DEFAULT TRUE,
                             id_equipe INTEGER REFERENCES t_arp_equipe(id) ON DELETE SET NULL
);