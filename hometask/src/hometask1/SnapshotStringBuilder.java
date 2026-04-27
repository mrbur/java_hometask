package hometask1;

import java.util.Stack;

public final class SnapshotStringBuilder {

    private StringBuilder data;
    private final HistoryStack historyStack = new HistoryStack();

    public SnapshotStringBuilder() {
        this.data = new StringBuilder();
    }

    public SnapshotStringBuilder(String stringData) {
        this.data = new StringBuilder(stringData);
    }

    public SnapshotStringBuilder append(String appendableString) {
        data.append(appendableString);
        return this;
    }

    public int indexOf(String dataString) {
        return data.indexOf(dataString);
    }

    public SnapshotStringBuilder insert(int  offset, Object o) {
        data.insert(offset, o);
        return this;
    }

    public String toString() {
        return data.toString();
    }

    /**
     * Сохранить состояние StringBuilder в стек снимков
     */

    public void snapshot() {
        historyStack.pushSnapshot(new HistoryStack.Snapshot(data));
    }

    /**
     * ПРиводит хранимый StringBuilder к состоянию из вершины стека снимков.
     * Метод безопасный, не пробрасывает внутренние ошибки
     * возвращает успешно ли восстановлено состояние или нет
     * @return true/false
     */
    public boolean undo() {
        try {
            var snapshot = historyStack.popSnapshot();
            data = snapshot.stringBuilder;
            return true;
        }
        catch (RuntimeException e) {
            return false;
        }

    }

    private static final class HistoryStack {
        private final Stack<Snapshot> snapshotStack = new Stack<>();

        private void pushSnapshot(final Snapshot snapshot) {
            if(snapshot != null) {
                snapshotStack.push(snapshot);
            }
        }

        private Snapshot popSnapshot() {
            if(!snapshotStack.empty()) {
                return snapshotStack.pop();
            }
            throw new RuntimeException("HistoryStack is empty");
        }

        private static final class Snapshot {
            private final StringBuilder stringBuilder;
            private Snapshot(final StringBuilder stringBuilderToSave) {
                //мы не знаем как менялся размер ранее в программе, потому сохраняем в точности
                if (stringBuilderToSave != null) {
                    this.stringBuilder = new StringBuilder(stringBuilderToSave.capacity()).append(stringBuilderToSave);
                }
                else {
                    throw new IllegalArgumentException("stringBuilderToSave is null");
                }
            }
        }
    }
}
