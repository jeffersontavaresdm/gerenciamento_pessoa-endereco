<div
    style="
    display: flex;
    flex-direction: column;
" >
  <h1 style="text-align: center" >Gerenciamento de Pessoa/Endereço</h1 >
  <h3 >Passo a passo para rodar a aplicação via terminal:</h3 >
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

  <h3 style="text-align: center" >Lista de endpoints da API</h3 >
  <div >
    <b >Pessoa:</b >
    <ul >
      <li >
        <a href="http://localhost:3100/pessoa/listar" >
          Listar pessoas
        </a >
      </li >
      <li >
        <a href="http://localhost:3100/pessoa/buscar/1" >
          Buscar pessoa por id
        </a >
      </li >
      <li >
        <a href="http://localhost:3100/pessoa/buscar?nome=nome&dataNascimento=2000-01-01/" >
          Buscar pessoa por nome e data de nascimento
        </a >
      </li >
      <li >
        <a href="http://localhost:3100/pessoa/criar" >
          Criar pessoa
        </a >
      </li >
      <li >
        <a href="http://localhost:3100/pessoa/editar/1" >
          Editar pessoa
        </a >
      </li >
    </ul >
    <b >Endereço:</b >
    <ul >
      <li >
        <a href="http://localhost:3100/endereco/listar/1" >
          Listar endereços da pessoa
        </a >
      </li >
      <li >
        <a href="http://localhost:3100/endereco/criar/1" >
          Criar endereço para pessoa
        </a >
      </li >
      <li >
        <a href="http://localhost:3100/endereco/definir-endereco-principal/1/1" >
          Definir endereço principal
        </a >
      </li >
    </ul >
  </div >
</div >