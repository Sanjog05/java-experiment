package io.keweishang.exception;

import java.io.IOException;

public class CheckedExceptionExample {

    public static void main(String[] args) {
        // have to catch a checked exception
        try {
            fnCheckedException();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // any exception that extends Exception is checked exception
        // except when it extends RuntimeException, then it's a unchecked exception
        try {
            fnCustomCheckedException();
        } catch (CustomException e) {
            e.printStackTrace();
        }

        // the following exception extends RuntimeException, so it's an unchecked exception
        // fnCustomRuntimeException();
    }

    private static int fnCheckedException() throws IOException {
        throw new IOException("a check exception");
    }

    private static int fnCustomCheckedException() throws CustomException {
        throw new CustomException();
    }

    private static int fnCustomRuntimeException() throws CustomRuntimeException {
        throw new CustomRuntimeException();
    }

    private static class CustomException extends Exception {
    }

    private static class CustomRuntimeException extends RuntimeException {
    }
}
