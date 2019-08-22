package com.mkyong.reactive;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestWeb {

    @Autowired
    private WebTestClient webClient;
    
    private static List<String> words = Arrays.asList("the", "quick", "brown", "fox","jumped","over","the", "lazy","dog");

      @Test
      public void simpleCreation() {
         Flux<String> fewWords = Flux.just("Hello", "World");
         Flux<String> manyWords = Flux.fromIterable(words).filter(p -> p.startsWith("t"));

         fewWords.subscribe(System.out::println);
         System.out.println();
         manyWords.subscribe(System.out::println);
      }
      
      @Test
      public void findingMissingLetter() {
        Flux<String> manyLetters = Flux
              .fromIterable(words)
              .flatMap(word -> Flux.fromArray(word.split(",")))
              .distinct()
              .sort();

        manyLetters.subscribe(System.out::println);
      }
      

    @Test
    public void zipping() {
        Flux<String> titles = Flux.just("Mr.", "Mrs.");
        Flux<String> firstNames = Flux.just("Prveen", "Jane");
        Flux<String> lastNames = Flux.just("Kumar", "Blake");
        Flux<String> fullname = Flux.zip(titles, firstNames, lastNames).map(names -> names.getT1() + " " + names.getT2() + " " + names.getT3() + " ");
        		fullname.subscribe(System.out::println);
        //StepVerifier.create(names).expectNext("Mr. John Doe", "Mrs. Jane Blake").verifyComplete();
        Flux<Long> delay = Flux.interval(Duration.ofMillis(5));
        Flux<String> firstNamesWithDelay = firstNames.zipWith(delay, (s, l) -> s);
        Flux<String> namesWithDelay = Flux
                .zip(titles, firstNamesWithDelay, lastNames)
                .map(t -> t.getT1() + " " + t.getT2() + " " + t.getT3());
        StepVerifier.create(namesWithDelay).expectNext("Mr. John Doe", "Mrs. Jane Blake").verifyComplete();
    }

}
