package com.agenda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Colleciones {
    public static void main(String[] args) {
        Map<String, List<String>> grupos = new HashMap<>();
        grupos.computeIfAbsent("backend", k -> new ArrayList<>())
              .add("Java");

        grupos.computeIfAbsent("backend", k -> new ArrayList<>())
              .add("Spring");

        System.out.println(grupos.get("backend")); // [Java, Spring]
    }
}
