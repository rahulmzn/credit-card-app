package com.bank.credit.card.constants;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResourcePaths {
    public static final String API         = "api";
    public static final String V1          = "/v1";
    public static final String ROOT_API    = "/" + API;
    public static final String ROOT_API_V1 = ROOT_API + V1;

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Card {
        public static final String NAME = "/cards";

        @NoArgsConstructor(access = AccessLevel.PRIVATE)
        public static class V1 {
            public static final String CARDS_ROOT = ROOT_API_V1 + NAME;
        }
    }
}
