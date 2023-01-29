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
</div >