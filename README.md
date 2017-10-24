# Melhores Filmes

Esse projeto é um client de acesso ao The Movie Data Base (https://www.themoviedb.org) via APi para requisições de filmes populares e detalhes de filmes via pesquisa por nome.

## Iniciando

O client possui 3 funções básicas:
- Listar filmes populares na inicialização;
- Pesquisar filmes por nome;
- Exibir detalhes do filme selecionado;

### Pré-requisitos

Para rodar o projeto é necessário ter instalado o Android Studio 2.3+, Android SDK versão 25 e Build Tools versão 26.0.2+

### Instalando

Copie a pasta completa do projeto para a pasta de projetos padrão do Android Sudio (AndroidStudioProjects).
Após o Build do Gradle, execute o projeto no emulador ou dispositivo conectado.

Altere o valor da @string "api_key" para sua chave (gerada no site do TMDB).

## Rodando os testes

O Projeto possui dois testes automatizados:
- ScroolMoviesAndDetailsMovieTest;
- SearchMovieTest;

Para rodar os testes, navegue de dentro do Android Studio até: app\src\androidTest\java\com\example\adeilsonassis\mytmdbclient

Clique com o botão direito do mouse sobre a classe do teste desejado e clique na opção Run.

### Entrando dentro dos testes

ScroolMoviesAndDetailsMovieTest: Essa classe testa a inicialização do aplicativo, bem como o carregamento dos filmes populares via API (Retrofit 2). Testa também a exibição dos 
detalhes do filme selecionado no RecyclerListView; com uso de delegate e EventBus para tratar as Threads secundárias.


SearchMovieTest: Essa classe testa a busca de filmes por nome e a exibição dos resultados encontrados no RecyclerListView.

## Construído com

Para o desenvolvimento do projeto, foram utilizadas as biblliotecas de terceiros abaixo listadas:

* [Retrofit2](http://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java
* [EventBus](http://greenrobot.org/eventbus/) - EventBus: Events for Android
* [Butterknife](http://jakewharton.github.io/butterknife/) - Field and method binding for Android views
* [Picasso](http://square.github.io/picasso/) - A powerful image downloading and caching library for Android
* [The Movie Database Org](https://www.themoviedb.org/) - Used to get movies list


## Autor

* **Adeilson Assis** - [AdeAssis](https://github.com/adeassis)