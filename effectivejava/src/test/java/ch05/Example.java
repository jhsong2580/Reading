package ch05;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.type.TypeReference;
import domain.ch05.item26.ClassLiteralSource;
import domain.ch05.item26.ClassLiteralSource.ClassLiteralMap;
import domain.ch05.item26.ClassLiteralSource.TypeReferenceMap;
import domain.ch05.item28.ChooserWithArray;
import domain.ch05.item28.Sub;
import domain.ch05.item28.Super;
import domain.ch05.item31.Child;
import domain.ch05.item31.GrandChild;
import domain.ch05.item31.MaxUtils;
import domain.ch05.item31.Stack;
import domain.ch05.item32.Variables;
import domain.ch05.item33.Factories;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;

public class Example {

    @Test
    public void WildcardTest() {
        //given
        List<?> lists = new ArrayList<>();

        //when
        lists.add(null); // null은 삽입이 가능하다.
        Object isNull = lists.get(0); //데이터 추출시 Object class로 설정된다.

        //then
        assertThat(isNull).isNull();

    }

    @Test
    public void ClassLiteralTest() {
        Class<List> listClass = List.class;
        Class<String[]> stringListClass = String[].class;
        Class<String> stringClass = String.class;
//        List<String>.class -> 컴파일 에러
//        List<?>.class;
        Type genericSuperclass = String.class.getGenericSuperclass();
    }

    @Test
    @DisplayName("Class Literal은 제너릭을 사용할수 없다")
    public void ClassLiteralTest1() {
        //given
        ClassLiteralSource source = new ClassLiteralSource();
        ClassLiteralMap unSafeMap = source.getUnSafeMap();

        //when
        List<String> list = new ArrayList<>();
        list.add("test");
        unSafeMap.put(List.class, list);

        //then
        List returnList = unSafeMap.get(
            List.class); // 클래스 리터털은 제너릭을 사용할수 없어, 반환된 List의 element들의 타입을 알수 없다.
        assertThat(returnList.get(0)).isEqualTo("test");
    }

    @Test
    public void SuperTypeTokenTest() {
        //given
        ClassLiteralSource source = new ClassLiteralSource();
        TypeReferenceMap safeMap = source.getTypeReferenceMap();

        //given
        List<String> list = new ArrayList<>();
        list.add("test");
        safeMap.put(new TypeReference<String>() {
        }, "abcd");
        /**
         * getClass().getGenericSuperclass() -> com.fasterxml.jackson.core.type.TypeReference<java.lang.String>
         * ((ParameterizedType) superClass).getActualTypeArguments() ->  class java.lang.String <- Class Object
         */
        safeMap.put(new TypeReference<List<String>>() {
        }, list);
        /**
         * getClass().getGenericSuperclass() -> com.fasterxml.jackson.core.type.TypeReference<java.util.List<java.lang.String>>
         * ((ParameterizedType) superClass).getActualTypeArguments() ->  java.util.List<java.lang.String> <- ParameterizedTypeImpl
         */

        //when
        String string = safeMap.get(new TypeReference<String>() {
        });
        List<String> strings = safeMap.get(new TypeReference<List<String>>() {
        }); // 클래스 타입을 TypeReference로 감싸, 제너릭을 클래스 리터럴로 못쓰는 문제를 해결한다.

        //then
        assertAll(
            () -> assertThat(string).isEqualTo("abcd"),
            () -> assertThat(strings.get(0)).isEqualTo("test"),
            () -> assertThat(strings.get(0).getClass()).isEqualTo(String.class)
        );
    }

    @Test
    public void 배열은_공변하다() {
        Sub[] subs = new Sub[3];
        Super[] supers = subs; // 배열은 공변하여, Sub가 Super의 하위 클래스이니 Sub[] 또한 Super[]의 하위 클래스이다.

        Object[] objectArray = new Long[1];
        try {
            objectArray[0] = "타입이 달라 ArrayStoreException을 던진다.";
        } catch (Exception e) {
            assertTrue(e instanceof ArrayStoreException);
            return;
        }

        assertFalse(true, "예외를 던져야합니다");

    }

    @Test
    public void 제네릭은_불공변하다() {
        List<Sub> subs = new ArrayList<>();
//        List<Super> supers = subs; //실패!
    }

    @Test
    public void 제네릭은_컴파일단계에서_타입체크를한다 (){
//        List<String>[] stringLists = new List<String>[1]; // 1()
//        List<Integer> intList = List.of(42); // 2()
//        Object[] objects = stringLists; // 3()
//        objects[0] = intList; // 4()
//        String s = stringLists[0].get(0); //5()
        /**
          * 1번이 실행이 된다고 가정하자
         * 3번이 실행이 됬을때 정상적으로 동작한다.
             * List<String>[] 은 런타임에 List[]로 보이게 된다. (제너릭 정보가 소거되기 때문)
             * List는 Object를 상속받는다.
             * 고로 Object[] 는 List[] 타입을 받을 수 있다.
         * 4번이 실행될때, 정상 동작을 하게 된다.
             * intList 는 런타임에서는 List 타입으로, 제너릭이 소거된다.
             * objects 또한 앞에서 List[] 타입으로 변경되었다.
             * 고로 objects[0] 에 intList를 추가할수 있게 된다.
         * 5번이 실행될때 에러가 발생한다.
             * 이렇게 되면 List<String>만 담겠다고 선언한 stringList 배열에 List<Integer>이 담기게 된다.
             * 컴파일러는 꺼낸 원소를 자동으로 String으로 형 변환 하게 되는데, 이 원소는 Integer이므로, 런타임에 ClassCastException이 발생되게 된다.
             * 이러한 문제때문에 제네릭 배열이 생성되지 않도록 컴파일 단계에서 에러를 발생시키는 것이다.
        */
    }

    @Test
    public void testStack (){
        //given
        Stack<Number> numberStack = new Stack<>();

        //when
        numberStack.push(Integer.valueOf(3));

        numberStack.pushAll(Arrays.asList(Integer.valueOf(3)).iterator());
        //then
    }

    @Test
    public void BoundedWildCardTest() {
        //given
        Child child_5 = new Child(5);
        Child child_10 = new Child(10);
        List<Child> childList = Arrays.asList(child_5, child_10);

        GrandChild grandChild_15 = new GrandChild(15);
        GrandChild grandChild_20 = new GrandChild(20);
        List<GrandChild> grandChildrenList = Arrays.asList(grandChild_15, grandChild_20);

        //when
        Child max = MaxUtils.max(childList, grandChildrenList);

        //then
        assertThat(max).isEqualTo(grandChild_20);
    }

    @Test
    public void VariableTest (){
        //given
        Variables<String> variables = new Variables<>();
        ; //
        assertAll(
            () -> assertThatThrownBy(
                () -> {
                    String[] strings = variables.pickTwo("a", "b", "c");
                }
            ).isInstanceOf(ClassCastException.class) // 하지만 함수의 결과를 컴파일러는 String[]으로 알고 있지만 실제는 Object[]이기 때문에 Cast가 되지 않는다.
        );
    }

    @Test
    public void FactoriesTest (){
        //given
        Factories factories = new Factories();

        //when
        factories.putFavorite(Integer.class, 3);
        factories.putFavorite(String.class, "3");

        //then
        assertAll(
            () -> assertThat(factories.getFavorite(Integer.class)).isEqualTo(3),
            () -> assertThat(factories.getFavorite(String.class)).isEqualTo("3")
        );
    }

    @Test
    public void CollectionTypeCheckTest (){
        //given
        Factories factories = new Factories();
        List<String> strings = new ArrayList<>();
        strings.add("a");

        //when
        factories.putFavorite(List.class, strings);
        List stringsTypeUnchecked = factories.getFavorite(List.class); // 제너릭이기 때문에 <String> 정보가 사라진 List 정보만 남는다.
        List stringsTypeChecked = Collections.checkedList(stringsTypeUnchecked, String.class); // 이 List에 String만 넣을수 있다는, 타입 체크 기능을 추가한다

        //then
        assertAll(
            () -> assertDoesNotThrow(
                () -> stringsTypeUnchecked.add(true) // 바로 꺼낸 List는 에러가 발생하지 않는다.
            ),
            () -> assertThatThrownBy(
                () -> stringsTypeChecked.add(true) // 타입 체크로 감싼 List는 에러가 발생한다! 타입 안전하다.
            ).isInstanceOf(ClassCastException.class)
        );
    }



}
