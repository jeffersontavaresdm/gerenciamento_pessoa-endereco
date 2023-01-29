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
      Execute o seguinte comando para construir o projeto:
      <br >
      <b ><i >gradle build</i ></b >
    </li >
    <li >
      Em seguida, execute o seguinte comando para iniciar a aplicacão:
      <br >
      <b ><i >./gradlew bootRun</i ></b >
    </li >
    <li >
      Se a aplicacão foi construída e iniciada corretamente, você deve ver a seguinte saída no terminal:
      <br >
      <b style="color: darkgray" >
        ...
        [INFO] Tomcat started on port(s): 3100 (http) with context path ''
        ...
      </b >
    </li >
    <li >
      Em seguida, você pode acessar a aplicacão digitando a seguinte URL:
      <br >
      <b style="color: darkgray" >http://localhost:3100</b >
      <br >
      <i >Exemplo: </i ><b style="color: darkgray" ></b >
      <a href="http://localhost:3100" >http://localhost:3100/pessoa/listar</a >
    </li >
  </ul >
</div >