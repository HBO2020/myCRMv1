import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { ContactFournisseurService } from '../service/contact-fournisseur.service';

import { ContactFournisseurComponent } from './contact-fournisseur.component';

describe('ContactFournisseur Management Component', () => {
  let comp: ContactFournisseurComponent;
  let fixture: ComponentFixture<ContactFournisseurComponent>;
  let service: ContactFournisseurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ContactFournisseurComponent],
    })
      .overrideTemplate(ContactFournisseurComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ContactFournisseurComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ContactFournisseurService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.contactFournisseurs?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
