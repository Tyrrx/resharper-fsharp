// ${COMPLETE_ITEM:override P}
module Foo

[<AbstractClass>]
type Base() =
    abstract P: int

type A() =
    inherit Base()

    {caret}