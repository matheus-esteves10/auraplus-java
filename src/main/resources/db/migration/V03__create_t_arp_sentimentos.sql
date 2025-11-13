-- ============================================
-- TABELA: SENTIMENTOS
-- ============================================
CREATE TABLE T_ARP_SENTIMENTOS (
                                   id SERIAL PRIMARY KEY,
                                   nome_sentimento VARCHAR(100) NOT NULL,
                                   valor_pontuacao NUMERIC(5,2),
                                   data TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                   descricao VARCHAR(255),
                                   id_usuario INTEGER REFERENCES t_arp_users(id) ON DELETE CASCADE
);