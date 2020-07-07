package homework5_Java2;

import java.lang.*;

//          Отличие первого метода от второго:
//          Первый просто бежит по массиву и вычисляет значения.
//          Второй разбивает массив на два массива, в двух потоках высчитывает новые значения и потом склеивает эти массивы обратно в один.

//          По замерам времени:
//          Для первого метода надо считать время только на цикл расчета:
//            for (int i = 0; i < size; i++) {
//                arr[i] = (long) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
//            }
//          Для второго метода замеряете время разбивки массива на 2, просчета каждого из двух массивов и склейки.

public class Main {

    public static void main(String[] args) {

//          Вызов первого задания со стандартным решением;
        Main.doMethodOne();

//          Вызов задания с многопоточностью;
        Main.doMethodTwo();
    }

    public static void doMethodOne(){

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

//          5) В консоль выводим время работы:
        System.out.println(System.currentTimeMillis() - startTime + ": расчёт времени для первого метода");
    }

    public static void doMethodTwo(){
        final int size = 10000000;
        final int newSize = size / 2;
        float[] arr = new float[size];
        float[] arr1 = new float[size];
        float[] arr2 = new float[size];

        for (int i = 0; i < size; ++i) {
            arr[i] = 1;
        }

        long startTime = System.currentTimeMillis();

        System.arraycopy(arr, 0, arr1, 0, newSize);
        System.arraycopy(arr, newSize, arr2, 0, newSize);

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i < arr1.length; i++) {
                    arr1[i] = (float) (arr1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i < arr2.length; i++) {
                    arr2[i] = (float) (arr2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        System.arraycopy(arr1, 0, arr, 0, newSize);
        System.arraycopy(arr2, 0, arr, newSize, newSize);

        System.out.println(System.currentTimeMillis() - startTime + ": расчёт времени для второго метода, с многопоточностью");
    }
}