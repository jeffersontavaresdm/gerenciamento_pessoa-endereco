<div
    style="
    display: flex;
    flex-direction: column;
" >
  <h1 style="text-align: center" >Gerenciamento de Pessoa/Endereço</h1 >
  <h3 >Rodar a aplicação:</h3 >
  <ul >
    <li >Acesse o diretório da aplicacão através do terminal.</li >
    <li >Execute o comando: <b ><i >gradle clean build</i ></b ></li >
    <li >
      Execute o comando: <i ><b >java -jar build/libs/gerenciamento_pessoa-endereco-1.0.0.jar</b ></i >
    </li >
  </ul >
  <h3 >Rodar a aplicação via gradle:</h3 >
  <ul >
    <li >Acesse o diretório da aplicacão através do terminal.</li >
    <li >
      Execute ocomando: <b ><i >gradle bootRun</i ></b >.
      <br >
      Se a aplicacão foi construída e iniciada corretamente, você deve ver a seguinte saída no terminal:
      <br >
      <span style="color: darkgray" >
        ...
        <br >
        [INFO] Tomcat started on port(s): 3100 (http) with context path ''
        <br >
        ...
      </span >
    </li >
    <li >
      Para acessar a aplicacão, digite a seguinte URL:
      <br >
      <span style="color: darkgray" >http://localhost:3100</span >
      <br >
      <i >Exemplo: </i >
      <b style="color: darkgray" >
        <a href="http://localhost:3100" >http://localhost:3100/pessoa/listar</a >
      </b >
    </li >
  </ul >
  <h3 >Rodar a aplicação em um container</h3 >
  <h4 >via Dockerfile:</h4 >
  <ul >
    <li >
      Acesse o diretório da aplicacão através do terminal.
    </li >
    <li >
      Execute o comando: <b ><i >gradle clean build</i ></b >
    </li >
    <li >
      Execute o comando: <b ><i >docker build -t [nome-da-imagem-que-quiser] ./</i ></b >
    </li >
    <li >
      Execute o comando: <b ><i >docker run -p 3100:3100 [nome-da-imagem-que-escolheu]</i ></b >
    </li >
  </ul >
  <h4 >via Docker Compose:</h4 >
  <ul >
    <li >
      Acesse o diretório da aplicacão através do terminal.
    </li >
    <li >
      Execute o comando: <i ><b >gradle bootBuildImage</b ></i >
    </li >
    <li >
      Execute o comando: <i ><b >docker compose up</b ></i >
    </li >
  </ul >
  <h3 >Comandos:</h3 >
  <ul >
    <li >
      gradle clean: <i >Remove todos os arquivos gerados anteriormente pelo Gradle.</i >
    </li >
    <li >
      gradle build: <i >Realiza a construção completa do projeto, compilando o código fonte, rodando testes, gerando
      artefatos (por exemplo, JARs, WARs, etc.), e outras tarefas definidas no arquivo <b >build.gradle</b >.</i >
    </li >
    <li >
      gradle bootRun: <i >Compila e inicializa a aplicação <b >Spring Boot</b >, sem que seja necessário construir um
      artefato (como um JAR) e executá-lo manualmente.
      <br >
      Em vez disso, o <b >Gradle</b > usa a classe principal da aplicação <b >Spring Boot</b > para iniciá-la como um
      aplicativo <b >standalone</b >.
    </i >
    </li >
    <li >
      docker build -t [nome-imagem] ./: <i >
      Constrói uma imagem <b >Docker</b > a partir do contexto atual ("./") e
      atribui o nome especificado ([nome-imagem]).
    </i >
    </li >
    <li >
      docker run -p 3100:3100 [nome-imagem]: <i >
      Inicia um novo container a partir da imagem <b >Docker</b > especificada ([nome-imagem]).
      <br >
      O argumento "-p 3100:3100" mapeia a porta 3100 no <b >host</b > para a porta 3100 no <b >container</b >,
      permitindo que a aplicação no container seja acessível externamente através da porta <b >3100</b >.
    </i >
    </li >
    <li >
      gradle bootBuildImage: <i >
      Constroi uma imagem <b >Docker</b > a partir de um projeto de aplicação <b >Spring Boot</b >.
      <br >
      Ele usa o <b >Gradle</b > como gerenciador de build e o plugin <b >spring-boot-gradle-plugin</b > para empacotar a
      aplicação como uma imagem <b >Docker</b >.
    </i >
    </li >
    <li >
      docker compose up: <i >
      Utiliza as configurações feitas no arquivo <b >docker-compose.yml</b >
      para criar e executar os serviços definidos no arquivo.
    </i >
    </li >
  </ul >
  <h3 style="text-align: center" >Lista de endpoints da API</h3 >
  <div >
    <b >Pessoa:</b >
    <ul >
      <li >
        <span >Listar pessoas</span >
        <br >
        <span >Exemplo:</span >
        <a href="http://localhost:3100/pessoa/listar" target="_blank" >
          <b >http://localhost:3100/pessoa/listar</b >
        </a >
      </li >
      <li >
        <span >Buscar pessoa por ID</span >
        <br >
        <span >Exemplo:</span >
        <a href="http://localhost:3100/pessoa/buscar/" target="_blank" >
          <b >http://localhost:3100/pessoa/buscar/1</b >
        </a >
      </li >
      <li >
        <span >Buscar pessoa por nome e data de nascimento</span >
        <br >
        <span >Exemplo:</span >
        <a href="http://localhost:3100/pessoa/buscar?nome=nome&dataNascimento=2000-01-01" target="_blank" >
          <b >http://localhost:3100/pessoa/buscar?nome=nome&dataNascimento=2000-01-01</b >
        </a >
      </li >
      <li >
        <span >Criar pessoa</span >
        <br >
        <span >Exemplo:</span >
        <a href="http://localhost:3100/pessoa/criar" target="_blank" >
          <b >http://localhost:3100/pessoa/criar</b >
        </a >
      </li >
      <li >
        <span >Editar pessoa</span >
        <br >
        <span >Exemplo:</span >
        <a href="http://localhost:3100/pessoa/editar/1" target="_blank" >
          <b >http://localhost:3100/pessoa/editar/1</b >
        </a >
      </li >
    </ul >
    <b >Endereço:</b >
    <ul >
      <li >
        <span >Listar endereços da pessoa</span >
        <br >
        <span >Exemplo:</span >
        <a href="http://localhost:3100/endereco/listar/1" target="_blank" >
          <b >http://localhost:3100/endereco/listar/1</b >
        </a >
      </li >
      <li >
        <span >Criar endereço para pessoa</span >
        <br >
        <span >Exemplo:</span >
        <a href="http://localhost:3100/endereco/criar/1" target="_blank" >
          <b >http://localhost:3100/endereco/criar/1</b >
        </a >
      </li >
      <li >
        <span >Definir endereço principal</span >
        <br >
        <span >Exemplo:</span >
        <a href="http://localhost:3100/endereco/definir-endereco-principal/1/1" target="_blank" >
          <b >http://localhost:3100/endereco/definir-endereco-principal/1/1</b >
        </a >
      </li >
    </ul >
  </div >
</div >
