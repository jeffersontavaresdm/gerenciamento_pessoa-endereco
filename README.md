<div
    style="
    display: flex;
    flex-direction: column;
" >
  <h1 style="text-align: center" >Gerenciamento de Pessoa/Endereço</h1 >
  <h3 >Passo a passo para rodar a aplicação via gradle:</h3 >
  <ul >
    <li >Acesse o diretório da aplicacão através do terminal.</li >
    <li >
      Execute o seguinte comando para construir o projeto: <b ><i >gradle build</i ></b >
    </li >
    <li >
      Em seguida, execute o seguinte comando para iniciar a aplicacão: <b ><i >./gradlew bootRun</i ></b >
    </li >
    <li >
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
      Em seguida, você pode acessar a aplicacão digitando a seguinte URL:
      <br >
      <span style="color: darkgray" >http://localhost:3100</span >
      <br >
      <i >Exemplo: </i >
      <b style="color: darkgray" >
        <a href="http://localhost:3100" >http://localhost:3100/pessoa/listar</a >
      </b >
    </li >
  </ul >
  <h3>Rodar em um container Docker via Docker Compose:</h3>
  <ul>
    <li>
        Acesse o diretório da aplicacão através do terminal.
    </li>
    <li>
        Execute o seguinte comando para construir o projeto: <i><b>gradle build</b></i>
    </li>
    <li>
        Execute o seguinte comando do Docker Compose para criar o container e rodar a aplicação: <i><b>docker compose up --build</b></i>
    </li>
  </ul>
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
