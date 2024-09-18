import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        ArrayList<Jogador> jogadores;
        int j;

        do {
            jogadores = new ArrayList<>();  // Reinicia a lista de jogadores a cada iteração
            System.out.println("BEM VINDO AO JOGO DE TABULEIRO\nDigite quantos jogadores você quer criar: ");
            j = teclado.nextInt();

            // Garantir que o número de jogadores esteja entre 1 e 6
            while (j < 1 || j > 6) {
                System.out.println("Número de jogadores inválido. Por favor, selecione um número de jogadores entre 1 e 6.");
                j = teclado.nextInt();
            }

            teclado.nextLine();  // Consumir a nova linha após o próximoInt

            // Criar os jogadores
            for (int i = 0; i < j; i++) {
                System.out.println("Digite a cor do " + (i + 1) + "º jogador: ");
                String cor = teclado.nextLine();

                String tipo = "";
                boolean tipoValido = false;

                while (!tipoValido) {
                    System.out.println("Digite o tipo do jogador (azarado, normal, sortudo): ");
                    tipo = teclado.nextLine().toLowerCase();

                    if (tipo.equals("azarado") || tipo.equals("normal") || tipo.equals("sortudo")) {
                        tipoValido = true;  // Tipo é válido
                    } else {
                        System.out.println("Tipo inválido. Escolha entre azarado, normal, ou sortudo.");
                    }
                }

                // Utiliza a Factory para criar o jogador do tipo fornecido
                Jogador jogador = JogadorFactory.criarJogador(tipo, cor, 0, 0, 0, 0, false, false, false);
                jogadores.add(jogador);
            }

            // Verificar a diversidade de tipos de jogadores
            if (!verificarDiversidadeTipos(jogadores)) {
                System.out.println("Não há diversidade suficiente de tipos de jogadores.");
                System.out.println("Reiniciando a criação de jogadores para garantir diversidade...");
            }
        } while (!verificarDiversidadeTipos(jogadores));

        // Exibir informações dos jogadores criados
        for (int i = 0; i < jogadores.size(); i++) {
            Jogador jogador = jogadores.get(i);
            System.out.println("Jogador " + (i + 1) + " - Cor: " + jogador.getCor() + " - Tipo: " + jogador.getClass().getSimpleName());
        }

        // Obter a instância única do Tabuleiro usando o Singleton
        Tabuleiro tabuleiro = Tabuleiro.getInstancia(jogadores);
        tabuleiro.criacaoDoTabuleiro();

        // Inicializar as casas do tabuleiro com as cores dos jogadores
        for (int l = 0; l < j; l++) {
            tabuleiro.adicionarCorCasa(0, jogadores.get(l).getCor());
        }

        // Imprimir o estado inicial do tabuleiro
        tabuleiro.imprimirTabuleiro();

        // Selecionar o modo de jogo
        System.out.println("Selecione o modo de jogo: ");
        System.out.println("1 - Modo Normal");
        System.out.println("2 - Modo Debug");
        int opcao = teclado.nextInt();

        // Iniciar o jogo de acordo com a opção selecionada
        if (opcao == 1) {
            System.out.println("Iniciando o jogo no modo normal...");
            tabuleiro.TurnoDoJogo();
        } else if (opcao == 2) {
            System.out.println("Iniciando o jogo no modo debug...");
            tabuleiro.TurnoDoJogoDebug();
        } else {
            System.out.println("Opção inválida. Iniciando no modo normal por padrão...");
            tabuleiro.TurnoDoJogo();
        }

        teclado.close();
    }

    // Método para verificar a diversidade de tipos de jogadores
    private static boolean verificarDiversidadeTipos(ArrayList<Jogador> jogadores) {
        Set<String> tipos = new HashSet<>();
        for (Jogador jogador : jogadores) {
            tipos.add(jogador.getClass().getSimpleName());
        }
        return tipos.size() > 1;
    }
}
