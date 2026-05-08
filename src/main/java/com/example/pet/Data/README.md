# Guia de Implementação - Data Access Layer com JPA e Hibernate

## 📋 Resumo da Implementação

Este documento descreve a implementação do Data Access Layer (DAL) com JPA (Jakarta Persistence API) e Hibernate para o banco de dados SQLite `pets_perdidos.db`.

## 🗂️ Estrutura de Arquivos

A camada de dados está organizada na pasta `src/main/java/com/example/pet/Data/` com os seguintes componentes:

### Entidades JPA (mapeamento objeto-relacional)
- **Especie.java** - Mapeia tabela ESPECIE
- **PetData.java** - Mapeia tabela PET
- **RegistroPerdaData.java** - Mapeia tabela REGISTRO_PERDA
- **RegistroAchadoData.java** - Mapeia tabela REGISTRO_ACHADO

### Repositórios JPA (acesso a dados)
- **EspecieRepository.java** - CRUD e queries para Especie
- **PetDataRepository.java** - CRUD e queries para PetData
- **RegistroPerdaDataRepository.java** - CRUD e queries para RegistroPerdaData
- **RegistroAchadoDataRepository.java** - CRUD e queries para RegistroAchadoData

## 📚 Dependências Adicionadas

No `pom.xml`, foram adicionadas:

```xml
<!-- Spring Data JPA -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- SQLite JDBC Driver -->
<dependency>
    <groupId>org.xerial</groupId>
    <artifactId>sqlite-jdbc</artifactId>
    <version>3.46.0.0</version>
</dependency>

<!-- Hibernate SQLite Dialect -->
<dependency>
    <groupId>org.hibernate.orm</groupId>
    <artifactId>hibernate-community-dialects</artifactId>
</dependency>
```

## ⚙️ Configuração do Banco de Dados

No `application.properties`:

```properties
# JPA/Hibernate Configuration
spring.jpa.database-platform=org.hibernate.community.dialect.SQLiteDialect
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true

# SQLite Configuration
spring.datasource.url=jdbc:sqlite:pets_perdidos.db
spring.datasource.driver-class-name=org.sqlite.JDBC
spring.datasource.username=
spring.datasource.password=
```

**Observações:**
- `ddl-auto=validate`: valida schema existente sem criar/modificar tabelas
- Banco SQLite deve estar no diretório raiz do projeto ou ajuste o caminho em `datasource.url`

## 🔗 Relacionamentos Mapeados

### Diagrama de Relacionamentos

```
ESPECIE (1) ──────── (N) PET
                       │
                       │ 1
                       │
                    (N) REGISTRO_PERDA
                       │
                       │ 1
                       │
                    (N) REGISTRO_ACHADO
```

### Descrição dos Relacionamentos

1. **Especie → PetData** (One-to-Many)
   - Uma espécie pode ter múltiplos pets
   - Mapeado com `@OneToMany(mappedBy = "especie")`

2. **PetData → RegistroPerdaData** (One-to-Many)
   - Um pet pode ter múltiplos registros de perda
   - Mapeado com `@OneToMany(mappedBy = "pet")`

3. **RegistroPerdaData → RegistroAchadoData** (One-to-Many)
   - Um registro de perda pode ter múltiplos registros de achado
   - Mapeado com `@OneToMany(mappedBy = "registroPerda")`

## 💻 Exemplos de Uso

### 1. Injetar Repositório em um Service ou Controller

```java
@Service
public class PetService {
    
    @Autowired
    private PetDataRepository petRepository;
    
    @Autowired
    private EspecieRepository especieRepository;
    
    // Métodos do serviço
}
```

### 2. Operações CRUD Básicas

```java
// CREATE - Salvar um novo pet
PetData novoPet = new PetData();
novoPet.setNome("Fluffie");
novoPet.setEspecie(especie);
novoPet.setRaca("Poodle");
PetData saved = petRepository.save(novoPet);

// READ - Buscar por ID
Optional<PetData> pet = petRepository.findById(1);

// READ - Buscar todos
List<PetData> todosPets = petRepository.findAll();

// UPDATE - Atualizar
pet.setNome("Novo Nome");
petRepository.save(pet);

// DELETE - Deletar
petRepository.deleteById(1);
```

### 3. Queries Personalizadas

```java
// Encontrar pets por raça
List<PetData> poodles = petRepository.findByRaca("Poodle");

// Encontrar pets por espécie
List<PetData> gatos = petRepository.findByEspecie(especieRepository.findByDescricao("Gato"));

// Encontrar registros de perda recentes (últimos 30 dias)
List<RegistroPerdaData> perdasRecentes = registroPerdaRepository.findRecentPerdas(30);

// Encontrar registros de perda não resolvidos
List<RegistroPerdaData> naoResolvidas = registroPerdaRepository.findUnresolvedPerdas();

// Encontrar registros de achado concluídos
List<RegistroAchadoData> concluidos = registroAchadoRepository.findConcluded();
```

### 4. Buscas com Múltiplos Critérios

```java
// Buscar pets por nome e espécie
List<PetData> resultados = petRepository.findByNomeAndEspecie("Fluffie", 1);

// Buscar registros de perda por múltiplos critérios
List<RegistroPerdaData> perdas = registroPerdaRepository.findByMultipleCriteria(
    1,                                    // petId
    "Centro de São Paulo",                // localPerda
    LocalDate.of(2024, 1, 1),            // dataInicio
    LocalDate.of(2024, 12, 31),          // dataFim
    null                                  // statusOcorrencia
);
```

### 5. Trabalhando com Relacionamentos

```java
// Recuperar todos os pets de uma espécie
Especie caes = especieRepository.findByDescricao("Cão");
List<PetData> todosCaes = petRepository.findByEspecie(caes);

// Recuperar registros de perda de um pet
PetData pet = petRepository.findById(1).orElse(null);
List<RegistroPerdaData> perdasDoPet = registroPerdaRepository.findByPet(pet);

// Recuperar achados de um registro de perda
RegistroPerdaData perda = registroPerdaRepository.findById(1).orElse(null);
List<RegistroAchadoData> achadosDaPerda = registroAchadoRepository.findByRegistroPerda(perda);

// Acessar dados relacionados (eager loading)
PetData pet = petRepository.findById(1).orElse(null);
String especieDescricao = pet.getEspecie().getDescricao(); // Já carregado (EAGER)
```

## 🔍 Métodos Disponíveis por Repositório

### EspecieRepository

| Método | Descrição |
|--------|-----------|
| `findById(Integer)` | Busca espécie por ID |
| `findAll()` | Retorna todas as espécies |
| `save(Especie)` | Salva ou atualiza espécie |
| `delete(Especie)` | Deleta espécie |
| `findByDescricao(String)` | Busca por descrição |
| `findByIndRastejante(Boolean)` | Busca espécies rastejantes |
| `findByIndVoador(Integer)` | Busca espécies voadoras |

### PetDataRepository

| Método | Descrição |
|--------|-----------|
| `findById(Integer)` | Busca pet por ID |
| `findAll()` | Retorna todos os pets |
| `save(PetData)` | Salva ou atualiza pet |
| `delete(PetData)` | Deleta pet |
| `findByNome(String)` | Busca por nome |
| `findByEspecie(Especie)` | Busca por espécie |
| `findByRaca(String)` | Busca por raça |
| `findByCor(String)` | Busca por cor |
| `findByPorte(Integer)` | Busca por porte |
| `findByDataNascimento(LocalDate)` | Busca por data de nascimento |
| `findByDataNascimentoBetween(LocalDate, LocalDate)` | Busca em período |
| `findByNomeAndEspecie(String, Integer)` | Busca por nome e espécie |

### RegistroPerdaDataRepository

| Método | Descrição |
|--------|-----------|
| `findById(Integer)` | Busca perda por ID |
| `findAll()` | Retorna todas as perdas |
| `save(RegistroPerdaData)` | Salva ou atualiza perda |
| `delete(RegistroPerdaData)` | Deleta perda |
| `findByPet(PetData)` | Busca perdas de um pet |
| `findByLocalPerda(String)` | Busca por local |
| `findByDataPerda(LocalDate)` | Busca por data |
| `findByDataPerdaBetween(LocalDate, LocalDate)` | Busca em período |
| `findRecentPerdas(Integer)` | Busca últimos N dias |
| `findUnresolvedPerdas()` | Busca perdas não resolvidas |
| `findByMultipleCriteria(...)` | Busca com múltiplos filtros |

### RegistroAchadoDataRepository

| Método | Descrição |
|--------|-----------|
| `findById(Integer)` | Busca achado por ID |
| `findAll()` | Retorna todos os achados |
| `save(RegistroAchadoData)` | Salva ou atualiza achado |
| `delete(RegistroAchadoData)` | Deleta achado |
| `findByRegistroPerda(RegistroPerdaData)` | Busca achados de uma perda |
| `findByLocalAchado(String)` | Busca por local |
| `findByDataAchado(LocalDate)` | Busca por data |
| `findByDataAchadoBetween(LocalDate, LocalDate)` | Busca em período |
| `findByStatusOcorrencia(Integer)` | Busca por status |
| `findConcluded()` | Busca concluídos |
| `findPending()` | Busca pendentes |
| `findRecentAchados(Integer)` | Busca últimos N dias |
| `findByMultipleCriteria(...)` | Busca com múltiplos filtros |

## 🎯 Boas Práticas

### 1. Use Services para Lógica de Negócio

```java
@Service
public class PetService {
    
    @Autowired
    private PetDataRepository petRepository;
    
    @Transactional
    public PetData registrarNovoPet(String nome, Integer especieId) {
        // Lógica de negócio
        PetData novoPet = new PetData();
        novoPet.setNome(nome);
        // ... configurar outros campos
        return petRepository.save(novoPet);
    }
}
```

### 2. Use @Transactional para Operações Múltiplas

```java
@Transactional
public void registrarPerdaENotificar(Integer petId, String localPerda) {
    PetData pet = petRepository.findById(petId).orElseThrow();
    
    RegistroPerdaData perda = new RegistroPerdaData();
    perda.setPet(pet);
    perda.setLocalPerda(localPerda);
    perda.setDataPerda(LocalDate.now());
    
    registroPerdaRepository.save(perda);
    // Enviar notificação, etc
}
```

### 3. Trate Ausência de Registros

```java
Optional<PetData> pet = petRepository.findById(1);
if (pet.isPresent()) {
    // Trabalhar com o pet
} else {
    // Pet não encontrado
}

// Ou usar orElseThrow()
PetData pet = petRepository.findById(1)
    .orElseThrow(() -> new EntityNotFoundException("Pet não encontrado"));
```

### 4. Cuidado com Lazy Loading

```java
// Problema: LazyInitializationException se acessar depois da transação
List<PetData> pets = petRepository.findAll();
pets.forEach(p -> System.out.println(p.getRegistrosPerdas())); // Erro!

// Solução 1: Usar EAGER loading (já configurado nas entidades)
PetData pet = petRepository.findById(1).orElse(null);
System.out.println(pet.getEspecie().getDescricao()); // OK - EAGER

// Solução 2: Manter dentro da transação
@Transactional
public void processar() {
    List<PetData> pets = petRepository.findAll();
    pets.forEach(p -> System.out.println(p.getRegistrosPerdas())); // OK
}
```

## 🐛 Troubleshooting

### Erro: "No identifier specified for entity"

**Causa**: Falta anotação `@Id` na entidade

**Solução**: Adicionar `@Id` e `@GeneratedValue` na chave primária

### Erro: "LazyInitializationException"

**Causa**: Tentar acessar coleção lazy fora de transação

**Solução**: Usar `@Transactional` ou adicionar `.getHibernateInitializer()` ou mudar para EAGER

### Erro: "Column not found"

**Causa**: Nome da coluna no `@Column` não corresponde ao banco

**Solução**: Verificar nomes exatos das colunas com `PRAGMA table_info(tablename)`

### Erro: "A different object with the same identifier value was already associated"

**Causa**: Tentando mergear objetos desanexados sem cuidado

**Solução**: Usar `merge()` ou `refresh()` apropriadamente

## 📖 Referências

- [Spring Data JPA Documentation](https://spring.io/projects/spring-data-jpa)
- [Hibernate ORM Guide](https://hibernate.org/orm/documentation/)
- [Jakarta Persistence API](https://jakarta.ee/specifications/persistence/)
- [SQLite JDBC Documentation](https://github.com/xerial/sqlite-jdbc)

---

**Última atualização**: Abril 2024
**Versão do Spring Boot**: 4.0.4
**Java**: 17+
