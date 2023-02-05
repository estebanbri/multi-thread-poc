package app;

import java.util.Base64;
import java.util.concurrent.CompletableFuture;

public class CompletableFutureJob {

    void runAsync() {
        CompletableFuture.runAsync(() -> {
            System.out.println("Hola");
        });
    }

    void supplyAsync() {
        CompletableFuture
                .supplyAsync(this::getJsonFromEndpoint)
                .thenApply(result -> this.base64Encoding(result))
                .thenAccept(System.out::println)
                .join(); // evita usar el join sobre el thread main,
        // solo para mostrar el ejemplo le puse un join() que bloquea la ejecucion del
        // current thread hasta que se complete el completablefuture, lo idal es crear un
        // thread paralelo al main thread y ese thread paralelo si ponele el join o get que
        // son bloqueantes pero el main thrad nunca se te bloquea
    }

    String getJsonFromEndpoint() {
        simulateSlowEndpoint();
        return "{key=123}";
    }

    String base64Encoding(String toEncode) {
        return Base64.getEncoder().encodeToString(toEncode.getBytes());
    }

    private void simulateSlowEndpoint() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
