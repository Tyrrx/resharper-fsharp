// ${COMPLETE_ITEM:override P}
module Foo

[<AbstractClass>]
type Base() =
    abstract P: int
    default this.P = 1

type A() =
    inherit Base()

    {caret}