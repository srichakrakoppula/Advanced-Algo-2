import random

def miller_rabin(n, k=10):
  """ Miller-Rabin test for primality
  >>> miller_rabin(11)
  True
  >>> miller_rabin(21)
  False
  >>> miller_rabin(130543930748028840018705051047)
  True
  >>> miller_rabin(100**10-1)
  False
  """
  if n == 2:
    return True
  if not n & 1:
    return False

  def check(a, s, d, n):
    x = pow(a, d, n)
    if x == 1:
      return True
    for i in range(s - 1):
      if x == n - 1:
        return True
      x = pow(x, 2, n)
    return x == n - 1

  s = 0
  d = n - 1
  while d % 2 == 0:
    d >>= 1
    s += 1

  for i in range(k):
    a = random.randrange(2, n - 1)
    if not check(a, s, d, n):
      return False
    return True


def generate_safe_prime(n=200):
    while(True):
        randx=random.getrandbits(n-1)
        if miller_rabin(randx):
            #print(randx, " is prime")
            p = 2*randx + 1
            if miller_rabin(p):
                print(p, " is safe ", n, "-bit prime number")
                return p

for i in range(100,500,100):
     generate_safe_prime(i)

if __name__ == "__main__":
    import doctest
    doctest.testmod(verbose=True)
     
#eg., p=1259964253369724338681322166160201874470632393691592420656907


