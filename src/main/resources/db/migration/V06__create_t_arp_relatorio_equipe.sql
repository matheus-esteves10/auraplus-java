-- ============================================
-- TABELA: RELATÓRIO DE EQUIPE
-- ============================================
CREATE TABLE T_ARP_RELATORIO_EQUIPE (
                                        id SERIAL PRIMARY KEY,
                                        data TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                        sentimento_medio VARCHAR(100),
                                        descritivo VARCHAR(255),
                                        id_equipe INTEGER REFERENCES t_arp_equipe(id) ON DELETE CASCADE
);