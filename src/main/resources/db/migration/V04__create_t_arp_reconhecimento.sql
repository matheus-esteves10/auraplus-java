-- ============================================
-- TABELA: RECONHECIMENTO
-- ============================================
CREATE TABLE T_ARP_RECONHECIMENTO (
                                      id SERIAL PRIMARY KEY,
                                      titulo VARCHAR(100) NOT NULL,
                                      descricao VARCHAR(255),
                                      data TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      id_reconhecedor INTEGER REFERENCES t_arp_users(id) ON DELETE CASCADE,
                                      id_reconhecido INTEGER REFERENCES t_arp_users(id) ON DELETE CASCADE
);