package ru.clevertec.check.paramresolver;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import ru.clevertec.check.exception.CheckException;
import ru.clevertec.check.repository.sql.SqlRepository;

public class SqlRepositoryParamResolver implements ParameterResolver {

    private static final String url = "jdbc:postgresql://localhost:5432/test_test";
    private static final String username = "test";
    private static final String password = "test";

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == SqlRepository.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        var store = extensionContext.getStore(ExtensionContext.Namespace.create(SqlRepository.class));
        return store.getOrComputeIfAbsent(SqlRepository.class, it -> {
            SqlRepository repository = new SqlRepository(url, username, password);
            try {
                repository.load();
            } catch (CheckException e) {
                throw new RuntimeException(e);
            }
            return repository;
        });
    }

}
