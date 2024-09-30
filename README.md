## Jogo de Xadrez - Projeto Udemy - Prof Nélio Alves

Este projeto é uma implementação de um jogo de xadrez em Java, desenvolvido durante o curso de programação do professor Nélio Alves da plataforma Udemy. O projeto demonstra conceitos importantes de programação orientada a objetos, como:

* **Abstração:**  Classes abstratas como `Peca` definem comportamentos e atributos comuns a todas as peças de xadrez.
* **Encapsulamento:**  Atributos privados protegem o estado interno das classes e métodos públicos permitem acesso controlado.
* **Polimorfismo:**  O método `movimentosPossiveis()` é implementado de forma específica em cada subclasse de `Peca`, refletindo os movimentos únicos de cada peça.
* **Herança:**  A classe `PecaXadrez` herda de `Peca` e adiciona funcionalidades específicas para peças de xadrez, como a cor da peça.

### Recursos

* **Tabuleiro:** Representado pela classe `Tabuleiro`, com métodos para manipulação de peças, como `lugarPeca()`, `removerPeca()`, etc.
* **Peças:** Representadas por classes como `Peao`, `Torre`, `Cavalo`, `Bispo`, `Rainha` e `Rei`, cada uma com sua lógica de movimento.
* **Partida:**  A classe `PartidaXadrez` gerencia o fluxo da partida, incluindo o turno atual, a verificação de xeque e xeque-mate, além de outros aspectos do jogo.
* **Posicionamento:**  A classe `XadrezPosicao` converte coordenadas de xadrez (ex: "a1", "h8") em posições numéricas no tabuleiro.

### Como Executar

1. **Clone o repositório:** Baixe o código-fonte do projeto.
2. **Compile o projeto:** Utilize um IDE como Eclipse ou IntelliJ IDEA para compilar o código.
3. **Execute a classe `PartidaXadrez`:**  Crie um objeto da classe `PartidaXadrez` e utilize os métodos disponíveis para iniciar e jogar uma partida de xadrez.

### Estrutura do Projeto

```
xadrez
├── xadrez
│   ├── Cor.java
│   ├── PartidaXadrez.java
│   ├── PecaXadrez.java
│   ├── XadrezPosicao.java
│   ├── XadrezException.java
│   └── pecas
│       ├── Peao.java
│       ├── Torre.java
│       ├── Cavalo.java
│       ├── Bispo.java
│       ├── Rainha.java
│       ├── Rei.java
└── jogodetabuleiro
    ├── TabuleiroException.java
    ├── Tabuleiro.java
    ├── Posicao.java
    └── Peca.java
```

### Observações

* Este projeto foi criado como um exercício para o aprendizado de Java.
* O código segue as diretrizes e diagrama UML do professor Nélio Alves.
* A lógica do jogo de xadrez está completa, mas o projeto pode ser aprimorado com recursos adicionais, como interface gráfica, diferentes níveis de dificuldade, etc.

### Agradecimentos

* Nélio Alves, por fornecer o curso de programação com conteúdo de alta qualidade.
* Plataforma Udemy por oferecer um vasto conteúdo sobre desenvolvimento de software e lógica de programação a seus alunos.
