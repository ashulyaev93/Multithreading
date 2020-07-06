package homework5_Java2;

//          Отличие первого метода от второго:
//          В первом методе просто бежим по массиву и вычисляем значение.
//          Второй метод разбивает массив на два массива, в двух потоках высчитывает новые значения и потом склеивает эти массивы обратно в один.

//          По замерам времени:
//          Для первого метода надо считать время только на цикл расчета:
//            for (int i = 0; i < size; i++) {
//                arr[i] = (long) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
//            }
//          Для второго метода замеряете время разбивки массива на 2, просчета каждого из двух массивов и склейки.

public class Main {

    public static void main(String[] args){

//          Вызов метода со стандартным решением;
        Main.oneArray();

//          Вызов многопоточного класса;
        new Thread(new MyRunnableClassArray()).start();
    }

    public static void oneArray(){

//          1) Создаём одномерный длинный массив:
        final int size = 10000000;
        float[] arr = new float[size];

//          2) Заполняем этот массив единицами;
        for (int i = 0; i < size; ++i) {
            arr[i] = 1;
        }

//          3) Засекаем время выполнения;
        long startTime = System.currentTimeMillis();

//          4) Проходим по всему массиву и для каждой ячейки считаем новое значение по формуле:
        for (int i = 0; i < size; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }

//          5) Проверяем время окончания метода:
        System.currentTimeMillis();

//          6) В консоль выводим время работы:
        System.out.println(System.currentTimeMillis() - startTime + ": расчёт для первого метода");
    }

    static class MyRunnableClassArray implements Runnable {

        @Override
        public void run() {

            final int size = 10000000;

//          1) Создаём одномерный длинный массив и массивы для разбивания и склеёки;
            final int newSize = size / 2;
            float[] arr = new float[size];
            float[] arr1 = new float[size];
            float[] arr2 = new float[size];

//          2) Заполняем массив arr единицами;
            for (int i = 0; i < newSize; ++i) {
                arr[i] = 1;
            }

//          3) Засекаем время выполнения;
            long startTime = System.currentTimeMillis();

//          4) Делим один массив на два;
            System.arraycopy(arr, 0, arr1, 0, newSize);
            System.arraycopy(arr, newSize, arr2, 0, newSize);

//          5) Проходим по двум массивам и для каждой ячейки считаем новое значение по формуле;
            for (int i = 0; i < size; i++) {
                arr1[i] = (long) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
            for (int i = 0; i < size; i++) {
                arr2[i] = (long) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }

//          6) Обратная склейка;
            System.arraycopy(arr1, 0, arr, 0, newSize);
            System.arraycopy(arr2, 0, arr, newSize, newSize);

//          Примечание:
//          System.arraycopy() – копирует данные из одного массива в другой;
//          System.arraycopy(массив-источник, откуда начинаем брать данные из массива-источника, массив-назначение, откуда начинаем записывать данные в массив-назначение, сколько ячеек копируем);

//          7) Проверяем время окончания метода;
            System.currentTimeMillis();

//          8) В консоль выводим время работы;
            System.out.println(System.currentTimeMillis() - startTime + ": расчёт для второго метода");
        }
    }
}