package com.alura.spring_desafio3;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringDesafio1Application implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(SpringDesafio1Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// 1 - Dada a lista de números inteiros abaixo, filtre apenas os números pares e imprima-os.
		List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5, 6);
		numeros.stream()
				.filter(n -> n % 2 == 0)
				.forEach(System.out::println);

		
		//2 - Dada a lista de strings abaixo, converta todas para letras maiúsculas e imprima-as.
		List<String> palavras = Arrays.asList("java", "stream", "lambda");
		palavras.stream()
				.forEach(p -> System.out.println(p.toUpperCase()));
		
		//ou
		List<String> palavrasAlura = Arrays.asList("java", "stream", "lambda");
		palavrasAlura.stream()
				.map(String::toUpperCase)
				.forEach(System.out::println); // Esperado: JAVA, STREAM, LAMBDA


		//3 - Dada a lista de números inteiros abaixo, filtre os números ímpares, multiplique cada um por 2 e colete os resultados em uma nova lista
		List<Integer> numerosDois = Arrays.asList(1, 2, 3, 4, 5, 6);
		List<Integer> novaListaDois = numerosDois.stream()
				.filter(n -> n % 2 != 0)
				.map(n -> n * 2)
				.collect(Collectors.toList());
		novaListaDois.forEach(System.out::println);

		//4 - Dada a lista de strings abaixo, remova as duplicatas (palavras que aparecem mais de uma vez) e imprima o resultado.
		List<String> palavrasDois = Arrays.asList("apple", "banana", "apple", "orange", "banana");
		palavrasDois.stream()
					.distinct()
					.forEach(n -> System.out.print(n+", "));

		System.out.println("");
		//5 - Dada a lista de sublistas de números inteiros abaixo, extraia todos os números primos em uma única lista e os ordene em ordem crescente.
		List<List<Integer>> listaDeNumeros = Arrays.asList(
            Arrays.asList(1, 2, 3, 4),
            Arrays.asList(5, 6, 7, 8),
            Arrays.asList(9, 10, 11, 12,19,20)
        );
		
		List<Integer> listaPrimos = listaDeNumeros.stream()
													.flatMap(n -> n.stream().filter(m -> {
														if(m<=1) return false;
														for (int i = 2; i <= Math.sqrt(m); i++) {
															if(m % i == 0) return false;
														}
														return true;
													}))
													.collect(Collectors.toList());
		listaPrimos.forEach(System.out::println);

		//ou. solução alura

		List<Integer> numerosPrimos = listaDeNumeros.stream()
                                                    .flatMap(List::stream)  // Achatar as sublistas em uma única stream
                                                    .filter(SpringDesafio1Application::ehPrimo)  // Filtrar apenas números primos
                                                    .sorted()               // Ordenar em ordem crescente
                                                    .collect(Collectors.toList()); // Coletar em uma lista

        System.out.println(numerosPrimos); // Saída esperada: [2, 3, 5, 7, 11]

		//6 - Dado um objeto Pessoa com os campos nome e idade, filtre as pessoas com mais de 18 anos, extraia os nomes e imprima-os em ordem alfabética. A classe Pessoa está definida abaixo
		List<Pessoa> pessoas = Arrays.asList(
			new Pessoa("Charlie", 19),
            new Pessoa("Alice", 22),
			new Pessoa("Hiago", 19),
            new Pessoa("Jessie", 15),
			new Pessoa("Yoda", 19),
            new Pessoa("McEnroe", 40),
            new Pessoa("Bob", 17)
        );

		pessoas.stream()
				.filter(p -> p.idade > 18)
				.sorted((p1, p2) -> p1.getNome().compareTo(p2.getNome()))
				.forEach(n -> System.out.println(n.nome));

		//ou solução alura
		pessoas.stream()
              .filter(p -> p.getIdade() > 18)
              .map(Pessoa::getNome)
              .sorted()
              .forEach(System.out::println); // Esperado: "Alice", "Charlie"

		//7 - Você tem uma lista de objetos do tipo Produto, onde cada produto possui os atributos nome (String), preco (double) e categoria (String).
		//Filtre todos os produtos da categoria "Eletrônicos" com preço menor que R$ 1000, ordene-os pelo preço em ordem crescente e colete o resultado em uma nova lista.
		List<Produto> produtos = Arrays.asList(
            new Produto("Smartphone", 800.0, "Eletrônicos"),
            new Produto("Notebook", 1500.0, "Eletrônicos"),
            new Produto("Teclado", 200.0, "Eletrônicos"),
            new Produto("Cadeira", 300.0, "Móveis"),
            new Produto("Monitor", 900.0, "Eletrônicos"),
            new Produto("Mesa", 700.0, "Móveis")
        );

		List<Produto> novaListaProdutos = produtos.stream()
													.filter(p -> p.getCategoria().equalsIgnoreCase("eletrônicos") && p.getPreco()<1000.0)
													.sorted((p1, p2) -> Double.valueOf(p1.getPreco()).compareTo(p2.getPreco()))
													.collect(Collectors.toList());
		novaListaProdutos.forEach(System.out::println);

		//ou solução alura
		List<Produto> produtosFiltrados = produtos.stream()
													.filter(p -> p.getCategoria().equals("Eletrônicos")) // Filtrar pela categoria
													.filter(p -> p.getPreco() < 1000)                   // Filtrar pelo preço
													.sorted((p1, p2) -> Double.compare(p1.getPreco(), p2.getPreco())) // Ordenar pelo preço
													.collect(Collectors.toList()); // Coletar em uma lista
		System.out.println(produtosFiltrados);

		System.out.println("");
		System.out.println("Só os 3 eltrônicos mais baratos");
		//8 - Tomando o mesmo código do exercício anterior como base, modifique o código para que a saída mostre apenas os três produtos mais baratos da categoria "Eletrônicos".
		novaListaProdutos = produtos.stream()
									.filter(p -> p.getCategoria().equalsIgnoreCase("eletrônicos"))
									.sorted((p1, p2) -> Double.valueOf(p1.getPreco()).compareTo(p2.getPreco()))
									.limit(3)
									.collect(Collectors.toList());

		novaListaProdutos.forEach(System.out::println);

		//ou solução alura
		List<Produto> produtosMaisBaratos = produtos.stream()
                                                    .filter(p -> p.getCategoria().equals("Eletrônicos")) // Filtrar pela categoria
                                                    .filter(p -> p.getPreco() < 1000)                   // Filtrar pelo preço
                                                    .sorted((p1, p2) -> Double.compare(p1.getPreco(), p2.getPreco())) // Ordenar pelo preço
                                                    .limit(3)                                           // Pegar os 3 primeiros
                                                    .collect(Collectors.toList());                     // Coletar em uma lista

        System.out.println(produtosMaisBaratos);
	}

	//ou. solução alura do método 5
	private static boolean ehPrimo(int numero) {
		if (numero < 2) return false; // Números menores que 2 não são primos
		for (int i = 2; i <= Math.sqrt(numero); i++) {
			if (numero % i == 0) {
				return false; // Divisível por outro número que não 1 e ele mesmo
			}
		}
		return true;
	};

}
