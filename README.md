# jDEC64
This project is a Java implementation of DEC64, a decimal floating point type.

It works by reinterpreting how the bits of a primitive long are to be understood.
The place to start reading is Doug Crockford's C / asm implementation at:

http://dec64.com/ 

There's also some good background reading in this post about floating point in
general, which explains (amongst other things) why we want to use 64 bits as the
basis of the decimal FP type:

http://lemire.me/blog/2017/02/28/how-many-floating-point-numbers-are-in-the-interval-01/

The overall roadmap (work in process) is:

1) Get feature complete 
2) Open-source, document and announce
3) Benchmark to ensure zero-allocation
4) Consider JIT behaviour and ensure proper inlining
5) If appropriate, look at JVM intrinsification for some operations (possibly
using the DEC64 implementation as a backend)

The initial target is parity with DEC64 by duplicating all of Doug's library
functionality in a fairly straight port.

Looking further our - who knows - perhaps one day it could even make it into 
a JVM language (e.g. Kotlin or Scala) as the basis for a new builtin type. 

## Getting started

Fork and clone this repo, then submit a PR to add yourself to the contributors
file. We haven't settled on a license yet, so the easiest thing is for
contributors to assign their copyright to @kittylyst, to allow the project to be
easily relicensing - so that it can be used in the widest possible range of
open-source projects without licensing hassles.

## Current tasks

After that, we have plenty still to implement even in Basic64 and the Math64
class. These include:

* Divide is still not fully working

* sqrt has ignored, failing tests

* inc is not correctly implemented for non-integers

* Formatting only does standard formatting at present

* Missing functionality in the ANTLR parser and REPL

* Lots of other broken things

