﻿using System.Collections.Generic;
using JetBrains.Annotations;
using JetBrains.ReSharper.Plugins.FSharp.Psi.Impl.Cache2.Parts;
using JetBrains.ReSharper.Psi;

namespace JetBrains.ReSharper.Plugins.FSharp.Psi.Impl.Cache2
{
  internal class FSharpModule : FSharpClass, IModule
  {
    public FSharpModule([NotNull] IClassPart part) : base(part)
    {
    }

    protected override IList<IDeclaredType> CalcSuperTypes() =>
      new[] {Module.GetPredefinedType().Object};

    public bool IsAnonymous =>
      this.GetPart<IModulePart>() is var modulePart &&  modulePart != null && modulePart.IsAnonymous;
  }
}
