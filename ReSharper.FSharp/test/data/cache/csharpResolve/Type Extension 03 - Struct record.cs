using static Module;

namespace ClassLibrary1
{
    public class Class1
    {
        public Class1()
        {
            T t = new T(field: 123);
            int f = t.Foo;
        }
    }
}
