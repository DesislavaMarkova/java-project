package playlist.commands;

/**
 * Интерфейс за дефиниране на конзолни команди.
 * Всяка команда трябва да имплементира метода execute.
 */
public interface Command {
    /**
     * Изпълнява логиката на командата.
     * @param args Аргументи, подадени от потребителя.
     * @return Резултат от изпълнението като текст.
     */
    String execute(String[] args);
}
