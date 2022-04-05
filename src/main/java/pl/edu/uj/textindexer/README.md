# TextIndexerRestful

1) Import as a maven project
2) Run TextIndexerRestfulApplication
3) Open Chrome
4) Paste in url
   ```http://localhost:8081/index?text=ala ma kota, kot koduje w Javie Kota```

   __Tip:__ this is equivalent
   to: ```http://localhost:8081/index?text=ala%20ma%20kota,%20kot%20koduje%20w%20Javie%20Kota```
5) Press *Enter* and and you will see the result:

```
a:ala, javie, kota, ma
d:koduje
e:javie, koduje
i:javie
j:javie, koduje
k:koduje, kot, kota
l:ala
m:ma
o:koduje, kot, kota
t:kot, kota
u:koduje
v:javie
w:w
```