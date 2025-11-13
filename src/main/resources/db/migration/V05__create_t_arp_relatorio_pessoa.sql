-- ============================================
-- TABELA: RELATÓRIO DE PESSOA
-- ============================================
CREATE TABLE T_ARP_RELATORIO_PESSOA (
                                        id SERIAL PRIMARY KEY,
                                        numero_indicacoes NUMERIC(5,0) DEFAULT 0,
                                        data TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                        descritivo VARCHAR(255),
                                        id_usuario INTEGER REFERENCES t_arp_users(id) ON DELETE CASCADE
);