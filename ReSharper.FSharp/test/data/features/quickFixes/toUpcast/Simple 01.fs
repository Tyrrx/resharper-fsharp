namespace global

type A() =
    class
    end

type B() =
    inherit A()

module Say =
    B() :?>{caret} A
