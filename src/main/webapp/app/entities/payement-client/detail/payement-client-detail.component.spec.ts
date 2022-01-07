import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PayementClientDetailComponent } from './payement-client-detail.component';

describe('PayementClient Management Detail Component', () => {
  let comp: PayementClientDetailComponent;
  let fixture: ComponentFixture<PayementClientDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PayementClientDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ payementClient: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PayementClientDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PayementClientDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load payementClient on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.payementClient).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
