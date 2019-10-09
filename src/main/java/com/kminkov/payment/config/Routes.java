package com.kminkov.payment.config;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Routes {

    @UtilityClass
    public static class Account {
        public static final String GET = "/accounts/:number";
        public static final String CREATE = "/accounts";
        public static final String DELETE = GET;
    }

    @UtilityClass
    public static class Transfer {
        public static final String CREATE = "/accounts/:number/transfers";
    }
}
