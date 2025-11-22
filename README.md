-link do vídeo de apresentação:

-link do pitch:

-link do deploy:

# 🌟 AuraPlus — Plataforma Inteligente de Engajamento e Bem-Estar de Equipes

O **AuraPlus** é uma solução web desenvolvida para melhorar o desempenho, o engajamento e o bem-estar de equipes corporativas.  
A plataforma permite que colaboradores enviem **recomendações** entre si e registrem diariamente seu **sentimento**, criando um ambiente de reconhecimento contínuo e acompanhamento emocional.

Ao final de cada mês, esses dados são enviados automaticamente para uma **IA generativa** que produz relatórios individuais e um relatório geral da equipe — auxiliando gestores a compreenderem o clima organizacional e a saúde emocional do time.

---

## 🚀 Principais Funcionalidades

### 👥 Reconhecimentos entre colaboradores
- Envio de recomendações após ajuda, colaboração ou atitude positiva.
- Estimula hábitos saudáveis e um ambiente colaborativo.

### 😊 Registro diário de sentimento
- Colaborador envia uma descrição livre sobre como se sente no dia.
- Dados utilizados na análise mensal da IA.

### 🤖 Geração automática de relatórios com IA
- Um **job schedulado** roda todo mês.
- Consolidado é enviado para uma **fila RabbitMQ**.
- Um **consumidor** processa a mensagem e aciona o **Spring AI (Groq API)**.
- São gerados:
    - 📄 Relatórios individuais
    - 📊 Relatório geral da equipe
- Insights sobre engajamento, clima e moral do time.

### 🔐 Autenticação e Autorização
- Implementado com **Spring Security** (roles como admin, gestor e colaborador).

### 🌍 Internacionalização (i18n)
- Suporte a múltiplas línguas.

### ⚡ Performance e Escalabilidade
- Implementação de **caching**.
- Paginação em recursos com grande volume.

### 🛰️ API REST completa
- Endpoints seguindo boas práticas de HTTP e códigos de status.

---

## 🧱 Arquitetura e Stack Tecnológico

| Tecnologia                     | Finalidade                 |
|--------------------------------|----------------------------|
| **Java 21**                    | Linguagem principal        |
| **Spring Boot**                | Framework base             |
| **Spring Data JPA**            | Persistência               |
| **Flyway**                     | Migrations                 |
| **Spring Security**            | Autenticação e autorização |
| **Bean Validation**            | Validações                 |
| **Caching do Spring**          | Performance                |
| **RabbitMQ**                   | Mensageria assíncrona      |
| **Spring AI**                  | Integração com IA          |
| **Groq API**                   | Geração de relatórios      |
| **Maven**                      | Build e dependências       |
| **Internacionalização (i18n)** | Multilíngue                |
| **Deploy na Azure**            | Publicação                 |
| **React Native**               | App Mobile (FE)            |

---

## 🛠️ Como Rodar o Projeto

### 1. Pré-requisitos
- **Java 21** instalado
- **Docker** para subir a imagem do banco de dados e RabbitMQ
- Adicionar a API KEY do Groq em `application.properties` (enviada no txt)

- ### 2. Na aplicação
- Para executar os jobs automáticos assim que a aplicação 
iniciar, descomente o método marcado com @PostConstruct 
na classe RelatorioScheduler:
-Na classe EnvioRelatorioService precisamos usar o método
calcularPeriodoMensalTest(), pois ele considera o mes atual e não o 
anterior (que é a ideia final). Isso é necessário para testes atualmente
pois não temos dados do mês anterior savos no banco.
- No método getFuncionariosDoMesPorEquipe da classe RelatorioUsuarioService,
substituir a linha final LocalDate mesAnterior = LocalDate.now().minusMonths(1); por final LocalDate mesAnterior = LocalDate.now().minusMonths(0),
já que também não temos dados do mês anterior para testes.

## 🧠 Fluxo Mensal do Processamento com IA

```mermaid
flowchart LR
    A[Job mensal executa] --> B[Coleta sentimentos e recomendações]
    B --> C[Envia mensagem para fila RabbitMQ]
    C --> D[Consumidor RabbitMQ]
    D --> E[Processa dados e chama Spring AI]
    E --> F[Relatório individual]
    E --> G[Relatório geral da equipe]
    F --> H[Armazenamento no banco]
    G --> H[Armazenamento no banco]
    H --> I[Disponível para gestores]
